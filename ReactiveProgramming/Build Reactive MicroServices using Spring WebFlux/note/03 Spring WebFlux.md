# Spring WebFlux

---

## Intro

Spring WebFlux는 **Reactive Programming**을 적용한 Web Application 프레임워크이다.

자세한 내용은 공식 문서에서 확인 가능한데, 크게 두 가지 방식으로 **엔드포인트**를 구현할 수 있다.

- **Annotated Controllers** : MVC에서도 흔히 사용하는 그 방식

- **Functional Endpoints**

---

## 간단한 논블로킹 API 구축

Annotated Controller를 사용하여 간단한 논블로킹 API 서비스를 구축한다.

### Flux

```java
   @GetMapping("/flux")
    public Flux<Integer> flux() {
        return Flux.just(1, 2, 3)
                .log();
    }
```

클라이언트 측에는 { 1, 2, 3 } 의 JSON이 리턴되는 것을 확인 가능하다.

단, `log()`를 통해 데이터의 흐름을 확인해보면 각 원소는 **별개의 이벤트 호출**에 의해 리턴된 결과임을 확인 가능하며, 그 과정에서 **스레드가 변경될 수도 있음**을 짚어볼 수 있다.

### Mono

```java
    @GetMapping("/mono")
    public Mono<String> helloWorldMono() {
        return Mono.just("Hello-World")
                .log();
    }    
```

Flux와 유사하다.

### 무한 스트림 API 구축 (SSE)

**스트리밍 Endpoint**를 손쉽게 구현할 수 있다.

이는 새로운 데이터가 발생함에 따라 클라이언트 측에 계속해서 update된 데이터를 발송하는 엔드포인트로,

**Server Send Events**(SSE)라는 개념으로 제공되는 서비스이다.

이러한 API를 WebFlux를 이용해 간단하게 구축할 수 있다.

```java
   @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> stream() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }
```

위와 같이 작성한 뒤 호출하면,

- Flux에 `interval`이 발생하여 초당 1개씩 데이터가 생성됨에 따라 클라이언트로 해당 값이 전달되는 모습이 나타난다.

- **스레드**에 주목해볼 필요가 있는데,
  
  - 최초 요청 인입 및 `onSubscribe`나 `request` 같은 이벤트는 흔히 사용하는 `nio` 스레드에서 처리되지만
  
  - 데이터를 전송하는 `onNext` 이벤트의 경우 **별도의 parallel 스레드**가 생성되어 처리되며, `nio` 스레드는 **논블로킹 상태로 release**됨을 확인 가능하다.
  
  - 클라이언트에서 호출을 중단했을 때의 `cancel` 이벤트도 확인 가능하다.

---

## JUnit5와 @WebFluxTest를 이용한 테스트 자동화

### 통합테스트와 단위테스트

애플리케이션은 흔히 Controller, Service, Repository 3계층으로 구성된다.

통합테스트는 이 모든 계층을 실제로 만든 뒤 정상작동 여부를 확인하는 테스트이고,

단위테스트는 그 중 하나의 계층의 정상작동 여부를 확인하기 위해 나머지 계층을 **Mock**으로 구성한 뒤 실행하는 테스트이다.

### 컨트롤러 위주의 단위테스트 구성

#### @WebFluxTest

컨트롤러와 관련된 테스트를 위해 사용한다.

컨트롤러나 필터 등 **웹 관련 구성 요소**만 스프링 빈으로 로드하여 테스트하도록 환경을 구성해준다.

- `@SpringBootTest`와 구별해야 한다.

- 어떤 컨트롤러를 사용하여 테스트할 것인지 등록해줘야 한다.

웹 계층만 집중적으로 테스트할 때 성능 및 편의 상 우수하다.

#### @AutoConfigureWebTestClient

`WebTestClient`는 `WebClient` 에서 테스트 관련 기능을 보완한 통신 인터페이스다.

`@AutoConfigureWebTestClient` 애노테이션을 붙여주면, `WebTestClient`에 대한 환경 구성을 자동으로 진행해준다.

- 물론 사용하지 않고 직접 구성할 수도 있지만, 굳이 그럴 필요가 없다.

#### WebTestClient

기능적으로 `WebClient`와 사실상 동일한데, **테스트 관련 기능**이 보완된 인터페이스다.

비동기 및 블로킹 테스트를 지원하는 데 특화되어 있다.

### 테스트 코드 예시

```java
  @Test
    void flux_approach3() {
        webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(listEntityExchangeResult -> {
                    var responseBody = listEntityExchangeResult.getResponseBody();
                    assert (Objects.requireNonNull(responseBody).size() == 3);
                });
    }
```

```java
   @Test
    void stream() {
        var flux = webTestClient
                .get()
                .uri("/stream")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNext(0L, 1L, 2L, 3L)
                .thenCancel()
                .verify();
    }
```

- 위와 같이 Flux, Mono, stream 리턴형에 대해 테스트코드를 작성할 수 있다.

- `@WebFluxTest` 에 등록된 컨트롤러를 `WebTestClient`로 호출하여, 각종 유틸 메서드로 검증할 수 있다.

---

## MongoDB를 사용한 Reactive Web 구성과 단위테스트

DB 연계에 대한 단위테스트도 JUnit을 이용해 편리하게 진행할 수 있다.

### 세팅

#### de.flapdoodle.embed.mongo

해당 라이브러리를 testImplementation 으로 추가하면, 

실제 MongoDB 서버를 설치 및 세팅하지 않아도 **테스트 환경에서** **임베디드 MongoDB**를 구축하여 사용할 수 있다.

#### @DataMongoTest

MongoDB 관련 데이터 엑세스 계층을 테스트하는 데 특화된 애노테이션으로, **데이터 엑세스 관련 Bean만 로드**한다.

- `@WebFluxTest`랑 성격이 비슷하다.

임베디드 MongoDB 라이브러리와 호환되어, 테스트를 빠르고 쉽게 수행할 수 있게 해준다.

#### ReactiveMongoRepository

Java에서 MongoDB에 쉽게 접근할 수 있도록 개발된 인터페이스다.

- 사용방법이 JPA와 유사하다.

데이터 엑세스 작업이 **반응형**으로 수행된다는 점에서 중요하다.

- CRUD 작업이 별도로 `block` 하지 않는 이상 **비동기적**으로 처리되며,

- 반환 타입도 Mono 혹은 Flux 이다.

### 테스트 코드 작성 관련

- DB를 임시로 연동하여 테스트하기 때문에, `@BeforeEach`와 `@AfterEach`를 사용하여 **적절한 테스트 데이터**를 입력 및 삭제해줄 필요가 있다.

```java
    @Test
    void saveMovieInfo() {
        var movieInfo = new MovieInfo(null, "Batman Begins1",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        var moviesInfoMono = movieInfoRepository.save(movieInfo).log();

        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo1 -> {
                    assertNotNull(movieInfo1.getMovieInfoId());
                    assertEquals("Batman Begins1", movieInfo1.getName());
                })
                .verifyComplete();
    }
```

- 위와 같은 방식으로, `ReactiveMongoRepository`가 제공하는 데이터 엑세스 함수와 Reactive 반환형에 대해 테스트 진행이 가능하다.

---

## Spring WebFlux 통합테스트

지금까지는 특수 애노테이션을 이용하여 각 레이어에 대한 단위테스트를 진행했다면,

이제 `@SpringBootTest` 를 이용한 **통합테스트**를 구현한다.

### 예제 코드 요약

- 뻔한 3레이어 애플리케이션을 MongoDB를 사용해 제작하고,

- GET, POST, PUT, DELETE 엔드포인트에 대해 `SpringBootTest` 를 작성한다.

- 결과적으로 보면, `WebFluxTest`와 `DataMongoTest`를 결합해둔 형태가 된다.

```java
    @Test
    void deleteMovieInfo() {
        var movieInfoId = "abc";

        webTestClient.delete()
                .uri(MOVIES_INFO_URL + movieInfoId)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);

        webTestClient.get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(2);
    }
```

---

## Spring WebFlux 단위테스트

이전까지 작성한 단위테스트는 다른 계층을 아예 무시했다면,

이번에는 `Mock`을 이용하여 다른 계층이 **존재하는 것처럼 만들어두고** 보다 제대로 된 단위테스트를 구현한다.

### mockito

단위테스트에 사용할 `MockBean` 객체 관련 라이브러리다.

컨트롤러 단위테스트를 위해서는 서비스 객체를 Mock으로 생성할 필요가 있는데,

`@MockBean` 을 이용하여 바인딩하면 마치 `@Autowired`된 것과 같이 사용할 수 있다.

### 예제 코드

```java
    @Test
    void getAllMovieInfos() {

        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        when(moviesInfoServiceMock.getAllMovieInfos()).thenReturn(Flux.fromIterable(movieInfos));

        webTestClient
                .get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }
```

- `when()` 은 Mock 객체가 특정 메서드로 호출받았을 때 어떤 응답을 주면 되는지 지정해주는 역할을 수행한다.

- 결과적으로, 위와 같이 작성하면 **실제 DB를 전혀 연계하지 않고도** 컨트롤러가 서비스를 거쳐 데이터를 엑세스한 것과 같은 단위테스트를 구현할 수 있다.

---

### ResponseEntity 와 조합한 Reactive Controller

`ResponseEntity`는 스프링 MVC에서 흔히 사용하는 응답객체인데, HTTP 통신 인터페이스로서 가지는 편리함이 상당하다.

이에 WebFlux에서도 `ResponseEntity`를 조합하여 사용하는 것이 유효하며, `Mono`, `Flux`와 적절히 조합할 시 비동기 API를 구성하는 데에도 지장을 주지 않을 수 있다.


