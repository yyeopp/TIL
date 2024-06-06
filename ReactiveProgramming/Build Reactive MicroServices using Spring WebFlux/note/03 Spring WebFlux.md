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


