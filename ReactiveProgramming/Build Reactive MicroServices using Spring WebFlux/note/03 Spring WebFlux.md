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




