# Reactive MicroService

---

## WebClient

> ChatGPT

### 1. 반응형 (Reactive)

- **비동기 데이터 스트림 처리:** WebClient는 리액터(Reactor) 라이브러리를 기반으로 하여, 비동기 스트림 데이터를 처리하는 데 최적화되어 있습니다.
- **Non-blocking I/O:** 요청과 응답이 블로킹 없이 비동기적으로 처리됩니다. 이는 높은 성능과 효율적인 리소스 사용을 가능하게 합니다.
- **Backpressure 지원:** 데이터 흐름을 조절하여 시스템이 과부하되지 않도록 합니다.

### 2. 비동기 (Asynchronous)

- **비동기 호출:** WebClient를 사용하면 HTTP 요청을 비동기적으로 보낼 수 있으며, 이를 통해 응답을 기다리는 동안 다른 작업을 수행할 수 있습니다.
- **콜백 및 CompletableFuture:** 비동기 작업의 결과를 처리하기 위해 콜백 또는 CompletableFuture를 사용할 수 있습니다.
- **Non-blocking API:** API 전체가 non-blocking으로 설계되어 있어, 동시성 문제를 최소화합니다.

### 3. 마이크로서비스 (Microservices)

- **경량 HTTP 클라이언트:** WebClient는 마이크로서비스 간의 통신을 위한 경량 HTTP 클라이언트로 설계되었습니다.
- **Feign과의 통합:** WebClient는 Spring Cloud Netflix Feign과 함께 사용할 수 있으며, 이는 HTTP 클라이언트를 생성하고 구성하는 과정을 단순화합니다.
- **확장성:** 비동기 및 반응형 특성 덕분에, WebClient는 많은 수의 요청을 효율적으로 처리할 수 있어 마이크로서비스 아키텍처에 적합합니다.

---

## WebClient를 이용한 애플리케이션 구성

```java
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
```

```java
@Component
@RequiredArgsConstructor
public class MoviesInfoRestClient {

    private final WebClient webClient;

    @Value("${restClient.moviesInfoUrl}")
    private String moviesInfoUrl;

    public Mono<MovieInfo> retrieveMovieInfo(String movieId) {
        var url = moviesInfoUrl.concat("/{id}");

        return webClient.get()
                .uri(url, movieId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .log();
    }
}
```

- `WebClientConfig`를 이용하여 공통 `WebClient` 객체를 생성한다.

- 특정 마이크로서비스로 향하는 `WebClient`를 위와 같이 하나의 클래스로 묶어두면, 구조적으로 이점을 가진다.
  
  - URL 같은 것은 프로퍼티에서 `@Value`로 읽어옴

### 서비스 간 통신의 예외 처리

마이크로서비스 간 통신이 이루어질 때, 오류 응답이 돌아오는 경우는 매우 쉽게 상정할 수 있다.

각 서비스로 향하는 `RestClient` 에서 HTTP StatusCode에 따른 분기처리와 GlobalErrorHandler를 적용하여 구조적으로 대응할 수 있다.

```java
 public Mono<MovieInfo> retrieveMovieInfo(String movieId) {
        var url = moviesInfoUrl.concat("/{id}");

        return webClient.get()
                .uri(url, movieId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(
                                new MoviesInfoClientException("NO", clientResponse.statusCode().value()));
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage
                                    -> Mono.error(new MoviesInfoClientException(responseMessage, clientResponse.statusCode().value())));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage
                                    -> Mono.error(new MoviesInfoServerException("Server Exception " + responseMessage)));
                })
                .bodyToMono(MovieInfo.class)
                .log();
    }
```

- 위에서 만들었던 RestClient에 `onStatus()` 를 이용해서 오류처리를 담았다.

```java
@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(MoviesInfoClientException.class)
    public ResponseEntity<String> handleClientException(MoviesInfoClientException exception) {
        log.error("Exception : {}", exception.getMessage());
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        log.error("Exception : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}

```

- MVC 때 사용했던 `@ControllerAdvice`를 거의 그대로 사용할 수 있다.

---

## WireMock 을 이용한 통합테스트

현재 구축된 서비스는 원칙적으로 서로 다른 3개의 서버가 모두 제각기 다른 포트에서 기동된 상태일 때만 테스트가 가능하다.

모두 켜둔 상태에서, curl 같은 걸 날려서 테스트해볼 수 있다.

```shell
curl -i http://localhost:8082/v1/movies/1
```

- `RestClient`를 여러 개 들고 있는, 클라이언트가 호출할 서버는 8082 포트로 떠있다.

- 함께 MSA를 구성하는 다른 두 개의 서버는 각각 8080, 8081로 떠있다.

- 모두 켠 상태에서 위와 같이 curl을 날려서 테스트해볼 수는 있다.

이는 그리 좋은 방법이라고 할 수 없다.

- 오류 응답이 돌아왔을 때, 각각의 서버 로그를 전부 까봐야 원인을 정확히 파악할 수 있다.

- 자동화 테스트라고 보기에 무리가 있다.

- 서버를 전부 켜고 있는 것도 문제다.

### WireMock

> ChatGPT

WireMock은 HTTP 기반의 API를 모킹(mocking)하고 스터빙(stubbing)하기 위한 도구입니다. 이를 통해 개발자는 실제 API가 준비되지 않았거나 접근할 수 없는 상황에서도 API와의 통합 테스트를 수행할 수 있습니다. WireMock은 주로 다음과 같은 기능을 제공합니다:

1. **모킹(Mock)**: 특정 HTTP 요청에 대한 모의 응답을 생성합니다. 이를 통해 실제 API 없이도 개발 및 테스트를 수행할 수 있습니다.
2. **스터빙(Stubbing)**: 특정 요청에 대해 미리 정의된 응답을 반환하도록 설정할 수 있습니다. 이를 통해 다양한 응답 시나리오를 테스트할 수 있습니다.
3. **레코딩(Recording)**: 실제 API와의 상호 작용을 기록하여 나중에 재사용할 수 있습니다. 이를 통해 실제 응답을 기반으로 모킹을 설정할 수 있습니다.
4. **재생(Playback)**: 녹화된 상호 작용을 재생하여 동일한 환경을 재현할 수 있습니다.
5. **동적 응답(Dynamic Response)**: 요청의 특정 파라미터나 헤더에 따라 동적으로 응답을 생성할 수 있습니다.
6. **연결 지연(Simulated Latency)**: 응답에 지연을 추가하여 네트워크 지연 상황을 시뮬레이션할 수 있습니다.

WireMock은 Java 기반의 라이브러리로, 독립 실행형 서버로 실행하거나 JUnit과 같은 테스트 프레임워크와 통합하여 사용할 수 있습니다. 이는 특히 마이크로서비스 아키텍처에서 각 서비스가 독립적으로 개발 및 테스트될 수 있도록 하는 데 유용합니다.

- **개발 환경에서의 API 모킹**: 백엔드 서비스가 완성되지 않았을 때 프론트엔드 개발을 계속 진행할 수 있도록 합니다.
- **테스트 자동화**: 통합 테스트 시 실제 API 대신 모의 API를 사용하여 테스트 속도와 안정성을 높일 수 있습니다.
- **신뢰성 테스트**: 다양한 실패 시나리오를 시뮬레이션하여 시스템의 신뢰성을 검증할 수 있습니다.

위 내용 중, **스터빙**을 이용하면 MSA 서비스에 대해 **로컬 환경**에서 간단한 통합테스트를 시행할 수 있다.

### 통합테스트 구성하기

```java

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 8084)   // spin up a http server in port 8084
@TestPropertySource(
        properties = {
                "restClient.moviesInfoUrl=http://localhost:8084/v1/movieInfos",
                "restClient.reviewsUrl=http://localhost:8084/v1/reviews",
        }
)
public class MoviesControllerIntgTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void retrieveMovieMyId() {
        var movieId = "abc";
        stubFor(get(urlEqualTo("/v1/movieInfos" + "/" + movieId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("movieinfo.json")));

        stubFor(get(urlPathEqualTo("/v1/reviews"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("reviews.json")));

        webTestClient.get()
                .uri("/v1/movies/{id}", movieId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Movie.class)
                .consumeWith(movieEntityExchangeResult -> {
                    var movie = movieEntityExchangeResult.getResponseBody();
                    assert Objects.requireNonNull(movie).getReviewList().size() == 2;
                    assertEquals("Batman Begins", movie.getMovieInfo().getName());
                });

    }

}
```

- WireMock이 가지는 static 메서드를 활용하여, **Stub**을 직접 구성하고 테스트케이스에서 활용하는 게 핵심이다.

- `stubFor` 메서드를 이용하여,
  
  - 특정 HTTP 메서드, URL 패턴에 대한
  
  - 응답의 헤더와 바디를 직접 구성하여
  
  - 마치 해당 서버가 구동되어 있는 것처럼 꾸며낼 수 있다.

- WireMock 서버가 `@AutoConfigureWireMock` 에 의해 8084번 포트에서 Stub 처리를 진행해준다.

```java
   @Test
    void retrieveMovieMyId_5XX() {
        var movieId = "abc";
        stubFor(get(urlEqualTo("/v1/movieInfos" + "/" + movieId))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("MovieInfo Service Unavailable")));

//        stubFor(get(urlPathEqualTo("/v1/reviews"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBodyFile("reviews.json")));

        webTestClient.get()
                .uri("/v1/movies/{id}", movieId)
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(String.class);

    }
```

- 앞서 구성한  `GlobalErrorHandler`에 대해서도 위와 같이 테스트를 실행해볼 수 있다.

---

## Retry 적용

MSA 환경에서 서비스 간 호출에서 일시적인 장애는 언제든지 발생할 수 있다.

HTTP Status 기준 4XX 같이 아예 호출을 잘못한 케이스가 아니라, 일시적인 부하 집중으로 인한 타임아웃 같은 5XX 에러에 대해서는,

**적절한 Retry** 정책이 적용되는 편이 더 안정적인 서비스 운영을 위해 필요하다.

이를 위해 `reactor.util.retry.Retry` 클래스를 잘 활용해본다.

### Retry 구

```java
public class RetryUtil {
    public static Retry retrySpec() {

        return Retry.fixedDelay(3, Duration.ofSeconds(1))
                .filter(ex -> ex instanceof MoviesInfoServerException
                        || ex instanceof ReviewsServerException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal)
                        -> Exceptions.propagate(retrySignal.failure()));
    }
}

```

- `WebClient` 에서 바인딩해서 사용 가능한 `Retry` 클래스를 생성한다.

- 다음과 같은 정책들을 설정할 수 있다.
  
  - 몇 번 Retry 할 건지
  
  - Retry 간의 주기를 어떻게 할 것인지
  
  - 어떤 Exception 에 대해서 Retry를 진행할 것인지
  
  - Retry가 종료됐을 때, 최종적으로 클라이언트에게 어떤 응답을 돌려줄 것인지
    
    - 예제코드에서는 목적지 서버에서 발생한 Exception을 그대로 전파시키는 내용으로 구성됨

이후 `WebClient` 를 호출하는 함수에,

`.retryWhen(RetryUtil.retrySpec())` 같은 형태로 묶어주면 구성 완료다.

### 테스트코드 구성

```java

    @Test
    void retrieveMovieMyId_Reviews_5XX() {
        var movieId = "abc";
        stubFor(get(urlEqualTo("/v1/movieInfos" + "/" + movieId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("movieinfo.json")));

        stubFor(get(urlPathEqualTo("/v1/reviews"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Review Service Unavailable")));

        webTestClient.get()
                .uri("/v1/movies/{id}", movieId)
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(String.class);

        verify(4,
                getRequestedFor(urlPathMatching("/v1/reviews*")));
    }
```

- `verify` 함수에서, 특정 URL로 향하는 요청이 테스트코드 전체에서 **몇 번 호출됐는지**를 검증할 수 있다.

- 위 코드는 Review Service에서 의도적으로 500 에러를 냈고, 3번의 retry가 발생하므로

- 결과적으로 4번의 호출이 발생한다.


