# Build Reactive MicroServices using Spring WebFlux / SpringBoot

Udemy

Pragmatic Code School

---

# Reactive Programming

---

## 왜 반응형 프로그래밍인가

### 프로그램 아키텍처의 변화

과거에는 아래와 같았다.

- Monolithic Application

- 베어메탈 Application 서버에 단일 배포

최근 추세는,

- **Microservices Application**

- 클라우드 환경에 **분산 시스템**으로 배포

### 앞으로의 예측

향후의 프로그램은 아래와 같은 스펙을 갖춰야 할 것으로 예측된다.

- 밀리세컨드 단위의 응답속도

- 무중단 배포

- 부하 기반의 자동 스케일 조정

### 스프링 MVC와 RestfulAPI

Spring MVC 기반으로 구성된 애플리케이션 서버는 **요청 당** 하나의 **스레드**를 생성하는 Blocking API를 구성하게 된다.

해당 스레드가 DB와 외부 API를 모두 사용한 뒤 클라이언트에게 응답해야 한다면, 고객에게 돌아가는 **Latency 값은 그 모든 요청이 순차적으로 완료된 후**이다.

- 해당 모델은 고객의 수요에 더 이상 맞지 않는다.

#### Spring MVC limitations

- 스프링 MVC 기준 내장톰캣의 스레드 풀 기본값은 **200**이다.
  
  - 이를 넘는 수의 동시 요청이 들어올 때 자동으로 스레드 풀을 증설할 수 있는 것이 가능하긴 하지만,
  
  - 10,000개 쯤까지도 커버할 수 있냐고 하면 불가능하다.

#### Thread and its limitations

쓰레드는 기본적으로 비싼 자원이다.

- 힙 메모리에서 대략 **1MB**정도를 차지한다.

쓰레드 개수가 증가하면 힙 메모리를 그만큼 차지하게 되고, 과도해질 시 앱 성능에 전반적인 약영향을 줄 수 있다.

#### 소결

Spring MVC를 이용해 Blocking API를 제공하는 것은, 서버 간의 호출이 빈번한 MSA 아키텍처를 구성하는 데에 부적절하다.

MSA 환경에서는 예측 불가능한 수준의 대량 동시접속이 발생할 수 있고, 이에 대해 단순히 내장톰캣 스레드를 증설하는 것으로는 대응이 어렵다.

### Spring MVC 수준에서 극복하기

Java에서 제공하는 스펙을 이용해서 이러한 Blocking API 구조를 개선해볼 수는 있다.

- Callbacks

- Future

#### Callbacks

비동기 호출을 가능하게 하는 Java 스펙으로, 콜백 함수를 파라미터로 집어넣는 방식이다.

하지만 코딩을 이렇게 해두면 **콜백지옥**이라고 흔히 얘기하는 모습이 된다.

- 유지보수가 너무 어렵다.

#### Future

Java 5에 추가된 스펙이다.

스레드와 상호작용하여 비동기 호출을 구현하는 부분을 **추상화**하여 제공한다.

하지만 여러 단점이 있는데,

- 여러 개의 Future를 호출했을 때 이 모든 결과들을 최종적으로 조합하는 것이 상당히 어렵다.

- `get()` 으로 호출 결과를 얻어내는 경우가 많은데, 이건 결국 Blocking API에 해당한다.

#### CompletableFuture

Java 8에 추가된 스펙이다.

비동기 호출을 함수형 프로그래밍과 함께 구현할 수 있도록 해준다.

Future와 비교할 때 MultipleFutures의 응답 결과를 조합하는 것이 상당히 쉬워졌다.

하지만,

- 여전히 Servlet 수준에서는 클라이언트 요청 한 개당 한 개의 스레드를 사용하고 있다는 점이 근본적인 제약.

### Spring MVC의 한계 정리

- 동시성의 제약

- Blocking API를 작성할 시 쓰레드 활용이 비효율적으로 이루어짐

- Java 스펙을 열심히 사용하여 아무리 코드를 최적화해도, 결국 Servlet 수준에서는 Blocking 스레드를 사용하게 됨

---

## 반응형 프로그래밍이란

### 개요

- 기본적으로 비동기, Non-Blocking

- 데이터는 **이벤트/메시지** 중심 **스트림**으로 흐르게 됨

- 함수형 프로그래밍 (람다, 스트림, Functional interface)

- BackPressure on Data Stream

#### 예시

App에서 DB로 쿼리와 함께 데이터 요청을 보낸다고 할 때,

Blocking API의 경우 단순히 요청에 대한 응답이 돌아올 때까지 쓰레드를 Block한 상태로 기다리게 된다.

Non-Blocking의 경우,

- 데이터를 요청한 뒤 스레드를 Block하지 않고 그 다음 처리를 진행

- App이 DB의 데이터를 받아서 처리할 준비가 됐으면, n개의 데이터를 **실제** 요청

- DB에서는 쿼리 결과를 대기하고 있다가 request에 의해 **스트림** 형태로 데이터를 전송
  
  - n개의 데이터를 요청했다면 1,2,3 순차적으로 `onNext` 이벤트의 개념으로 여러 번 응답
  
  - 다 전송됐으면 `onComplete()` 이벤트를 발생

- 이러한 데이터 전송에 대해, **데이터소스** 측의 데이터가 **스트림** 형태로 **PUSH**된다고 개념화한다.
  
  - **pressure**이라고도 표현됨

이와 같은 플로우는 기본적으로 Reactive 관련 라이브러리에서 추상화하여 제공해주기 때문에 코딩 레벨에서 크게 걱정하지 않아도 된다.

**Push-based data flow model** 이라고도 한다.

### Backpressure

App의 요청에 대한 응답이 **스트림 형태로 push**될 때, 자칫하면 App 쪽이 데이터 플로우에 overwhelm 상태가 될 수 있다.

Backpressure은 이러한 문제를 해결하는 측면으로 제시된다.

- 간단히 생각하면, `cancel()` 이벤트를 지원하는 것

- n개의 데이터가 필요하다고 할 때, 그 중 2개만 요청하고 그 결과를 `onNext` 이벤트로 받고 나서

- `cancel()` 이벤트를 발생시키면 데이터 스트림이 즉시 중단되도록 **지원**하는 것.

### Push-Pull based data flow model

단순히 데이터소스 측에서 데이터를 일방적으로 PUSH하는 것이 아니라, 

App의 이벤트 요청을 기반으로 데이터 스트림이 통제된다는 점에서 **Push-Pull based data flow model**이라고 말한다.

#### 반응형 프로그래밍을 언제 사용해야 하는가

데이터 소스 (DB, 다른 서버)에 대한 high load를 처리해야 할 때 적절하다.

- 400 TPS 이상이라고 대강 기준을 잡을 수도 있다.

### Reactive App 아키텍처

기본적으로 App이 Non-blocking으로 요청을 처리해야 한다.

**Netty**를 사용한다.

- MVC와 달리 요청 당 한 개의 스레드를 할당하는 개념이 아예 없다.

- **Event Loop Model**을 사용하여 클라이언트 요청을 처리한다.

코드 레벨에서도 Non-blocking이 적용되어야 한다.

- **Project Reactor**을 활용한다.

이를 조합한 것이 **Spring WebFlux**이다.

- Netty / Project Reactor

- Non-blocking Reactive API

### Reactive Streams

Reactive Streams는 여러 개발자들에 의해 표준화된 모델로, 4가지 인터페이스로 구성된다.

#### 4가지 인터페이스

- Publisher

- Subscriber

- Subscription

- Processor

#### Publisher

Publisher는 `subscribe(Subscriber<? super T> s)` 메서드 하나만을 가진다.

퍼블리셔는 **데이터 소스**의 추상화이다.

- DB, RemoteService 등이 이에 해당한다.

#### Subscriber

Subscriber는 4가지 메서드를 가진다.

- `onSubscribe(Subscription s)`

- `onNext(T t)`
  
  - 데이터 소스 (Publisher) 에서 Subscriber에게 데이터 스트림을 전달하는 요 메서드

- `onError(Throwable t)`

- `onComplete()`
  
  - 데이터 소스에서 Subscriber에게 더 이상의 데이터가 없음을 통지하는 중요 메서드

#### Subscription

2가지 메서드를 가진다.

- `request(long n)`

- `cancel()`

Subscriber가 Publisher에게 **데이터를 요청**하는 인터페이스이다.

데이터 스트림 상에서 n개 파트의 데이터를 요청하거나,

Backpressure의 개념으로 이를 취소할 수 있다.

**App과 Datasource를 연결**하는 인터페이스라고 할 수 있다.

#### Processor

Subscriber와 Publisher를 모두 `extend` 하는 인터페이스이다.

즉, 두 가지 행동을 모두 할 수 있는 인터페이스이다.

통상적으로 굳이 사용할 필요가 없다.

### Reactive Stream의 성공 플로우

- Subscriber가 Publisher에게 `subscribe()` 한다.

- Publisher가 `onSubscribe()` 하여 `Subscrption` 객체를 리턴한다.

- Subsccriber가 `Subscription` 객체를 이용해 `request(n)` 한다.

- Publisher가 `onNext(n)`을 이용해 데이터 스트림을 전송한다.

- Publisher가 최종적으로 `onComplete()`를 전송하여 스트림을 종료한다.

### Reactive Stream의 실패 플로우

- Subscriber가 `request(n)`한다.

- Publisher가 `onError()`에 따라 Subscriber에게 이벤트를 통지한다.
  
  - 단순히 Exception이 발생하는 것과 다르다.


