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

---

## 서비스 간 통신의 예외 처리

마이크로서비스 간 통신이 이루어질 때, 오류 응답이 돌아오는 경우는 매우 쉽게 상정할 수 있다.


