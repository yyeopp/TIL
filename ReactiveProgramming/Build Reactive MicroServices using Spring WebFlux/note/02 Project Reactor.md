# Project Reactor

---

## Project Reactor

### 개요

프로젝트 리액터는 Reactive Stream의 구현체로, **Reactive Library**이다.

Spring WebFlux에서 Non-Blocking API를 작성하기 위한 기본 라이브러리로 사용하고 있다.

### Flux와 Mono

Flux와 Mono는 Reactive Stream에서 사용하는 reactive type이다.

reactor-core 모듈에 포함된 클래스이며,

**Publisher**의 구현체에 해당한다.

- Flux는 0~N개의 element를 가진 Publisher이고

- Mono는 0~1개의 element를 가진 Publisher이다.

#### 차이점

`cancel()`이 작동하는지 여부에 따라 다르다고 보인다.

- Flux의 경우 N건의 데이터가 스트림 형태로 전달된다.

- Mono의 경우 단건 데이터가 전달된다.

- 둘 다 `onComplete`나 `onError` 로직은 존재하지만, `cancel()` 이 필요한 건 Flux 뿐이다.

#### operator

Flux와 Mono는 Publisher로서 operator에 의해 그 동작이 확장된다.

대표적으로는 filter, map 등이 있다.

- Java에서 스트림을 처리하는 것과 개념적으로 동일하다.

---

## Flux와 Mono

### 반응형 스트림 이벤트

```java
Flux.just("alex", "ben", "chloe").log();

fluxAndMonoGeneratorService.namesFlux()
                .subscribe((name) -> {
                    System.out.println("Name is : " + name);
                });
```

위와 같은 방식으로 간단하게 `Flux` 를 만들고 `subscribe`하여 사용할 수 있다.

`log()` 메서드를 달아두면, 해당 스트림이 작동하는 플로우를 로그에서 상세하게 확인 가능하다.

- Subscriber 측 `subscribe()` 최초 이벤트 발생 시

- Flux(Publisher) 측 `onSubscribe()` 이벤트에 의해 Subscription 객체가 리턴되고

- Subscriber가 `request(unbounded)` 이벤트를 발생시키면

- Flux 측 `onNext()` 에 의해 스트리밍이 진행되며

- 최종적으로 `onComplete()` 로 스트림이 종료된다.

`Mono` 에 대해서도 동일한 양상을 확인할 수 있다.

### JUnit5를 이용한 반응형 스트림 테스트

Project Reactor를 테스트코드에서 다루기 위해서는`build.gradle` 에 아래 의존성을 추가해야 한다.

`testImplementation("io.projectreactor:reactor-test:3.4.0")`

해당 라이브러리를 이용해 `StepVerifier` 와 같은 스트림 테스트용 클래스를 사용 가능하다.

```java
    @Test
    void namesFlux() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();
        StepVerifier.create(namesFlux)
//                .expectNext("alex", "ben", "chloe")
//                .expectNextCount(3)
                .expectNext("alex")
                .expectNextCount(2)
                .verifyComplete();

    }
```

- `StepVerifier`는 `create` 함수를 통해 스트림을 구독한 것과 같은 효과를 낸다.

- `expectNext()` 를 비롯한 여러 함수를 이용해 직관적으로 테스트코드 구현이 가능하다.

### 연산자를 이용한 스트림 데이터 변환

스트림에 담겨있는 데이터를 변환하여 리턴하는 작업은 애플리케이션 구현 과정에서 필수불가결하다.

이 때 `operator`를 사용하게 될 것이며, 다양한 연산자가 지원된다.

#### map()

`map()`은 스트림 각 원소를 다른 형태로 변환할 때 가장 흔하게 사용되는 연산자다.

주의할 점은 데이터 스트림은 기본적으로 **immutability** 속성을 가지고 있기 때문에, 스트림에 `map` 을 처리한 뒤 해당 스트림을  **구독하지 않았다면** `map` 처리 결과를 사용할 수 없다는 점이다.

#### filter()

각 스트림 원소를 Predicate 기반으로 필터링하는 연산자다.

데이터 스트림에서 `Predicate`를 만족하지 못하는 원소는 필터링되며,

`map` 이나 다른 `filter`를 연결하여 일종의 **파이프라인**을 구성할 수도 있다.

#### flatMap()

서로 다른 스트림을 인자로 받아서 하나의 스트림으로 **평준화**하여 Reactive Type 으로 리턴하는 연산자다.

**비동기**적 특성을 가지고 있다는 점에서 사용 시 주의해야 한다.

```java
    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringWithDelay)
                .log();
    }

    public Flux<String> splitStringWithDelay(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    @Test
    void namesFlux_flatmap_async() {
        int stringLength = 3;
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(3);

        StepVerifier.create(namesFlux)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }
```

- 위와 같이 `delayElements` 를 이용해 스트림의 각 원소를 랜덤한 순서로 리턴한 후, 테스트코드에서 해당 스트림을 호출하여 로그를 찍어본다.

- 그 결과 기존에 `map()` 을 다뤘을 때와 같은 **원소 간의 순서 보장**이 되지 않고 있는 모습을 볼 수 있다.
  
  - 랜덤값에 따라 리턴이 빨리 일어난 원소가 먼저 log에 남고 있다.

- 즉, `flatMap()` 이 인입된 원소를 변형하기 위해 호출하는 함수는 **비동기로 호출**된다.
  
  - **순서를 보장하지 않고**, 더 빨리 리턴되는 원소가 더 빨리 subscriber에게 돌아간다.

#### map과 flatMap의 차이

- `map`은 간단한 **동기성 1:1** 변환에 사용되지만, `flatMap`은 **비동기성 1:N** 변환에 주로 사용된다.

- `map`과 달리 `flatMap`은 subscriber에게 **평준화된 Reactive Type**을 리턴하고 있다.

- `map` 은 내부 연산에서 `Publisher`를 리턴하는 경우에 대해 지원해주지 못한다. `flatMap`은 `Publisher`를 리턴받아 **평준화**시키고 있기 때문에 가능하다.

#### concatMap()

스트림을 평준화한다는 점에서 `flatMap`과 동일하나, **동기 호출을 통해 요소 간의 순서를 보장**한다는 점에서 다르다.

`flatMap`과 거의 동일한 테스트코드를 사용했을 때,

- 요소 간의 순서는 정확히 보장해주고 있으나

- **앞 요소의 연산 처리가 종료된 후 그 다음 요소를 연산**한다는 점에서 `flatMap`에 비해 **전체적인 처리속도가 매우 느려진다**는 차이점이 발생한다.

서비스 구현 시 양자 간의 차이점을 정확히 인지하고 필요에 따라 사용하는 것이 중요.

### Mono 연산

#### flatMap()

Flux에서 사용하는 것과 동일하나,`Mono`이기 때문에 연산 결과가 **단일** 건이라는 점이 다르다.

`map()` 과 달리 데이터스트림을 **평준화**시킨다는 점에서 유의하여 사용한다.

#### flatMapMany()

`Mono` 형태의 데이터 스트림을 `Flux` 형태로 변환시킨다.

### 그 외 연산

#### transform()

`@FunctionalInterface` 를 인자로 입력받아 그에 해당하는 파이프라인 작업을 수행하는 메서드.

`filter`나 `map` 같은 일련의 동작이 반복된다면, `@FunctionalInterface`로 캡슐화하여 재사용하는 데 사용한다.

#### defaultIfEmpty()

반응형 스트림의 연산값이 비어있는 경우 리턴될 값을 지정하는 것.

#### switchIfEmpty()

반응형 스트림의 연산값이 비어있는 경우 **별도의 스트림으로 대체**하는 것.

---

## 반응형 스트림 결합

### 개요

`Mono`와 `Flux` 에 해당하는 여러 개의 반응형 스트림을 결합하는 일은 애플리케이션 구현 과정에서 굉장히 흔하다.

- 서로 다른 API나 DB를 여럿 조회하여 그 결과를 하나로 합치는 작업이 대표적

이러한 작업에 사용될 Reactor API를 분석한다.

### 연산자

#### concat과 concatWith

두 개의 반응형 스트림을 하나로 결합할 때 사용한다.

단, **순차적인 결합**이 필요할 때 사용하는 함수.

- 두 개의 스트림을 결합할 때,

- 앞쪽 스트림에 대한 subscribe가 끝나야 뒤쪽 스트림에 대한 subscribe를 처리하여

- 양 결과를 조합한다.

`concat()`은 `Flux`에서만 사용되고,

`concatWith`은 `Flux` `Mono` 양쪽에서 사용한다.

- 두 개의 `Mono`를 합친다는 점에서 `Flux`를 반환한다.

#### merge와 mergeWith

두 개의 Publisher를 하나로 합치는 데에 사용한다.

이는 Subscriber 쪽에서 **두 개의 Publisher**를 **동시에 구독**하기 때문에 가능한 일이다.

- `concat` 계열에서는 **순차적으로 구독**했다고 보면 된다.

- `merge`를 사용할 시, 각 Publisher에서 데이터를 주는 즉시 Subscribe하기 때문에 **순서가 보장되지 않는다.**

마찬가지로 `mergeWith`만 양쪽에서 사용 가능하다.

- `Flux`를 반환함

#### mergeSequential

`concat`과 `merge`를 합친 듯하게 작동한다.

두 Publisher를 동시에 구독하지만, **합치는 시점에서** **순서를 보장하게 된다.**

#### zip과 zipWith

**여러** 개의 Publisher를 하나로 합치는 데 사용하는데, 부가적으로 **람다 표현식**으로 이용해 **각 스트림에서 나온 요소들 간의 조합**을 가능케 한다.

인자로 입력된 모든 Publisher에서 요소가 하나씩 뽑혀나오는 것을 **기다렸다가** 람다로 입력된 표현식을 수행하여 Subscribe하는 방식으로,

Publisher들 중 하나가 `onComplete()`를 할 때까지 수행된다.

- 입력된 Publisher가 2개일 경우에만 람다 표현식 사용이 가능하고, 그 이상의 Publisher인 경우 `map` 함수를 이용해 **튜플** 표현식으로 스트림 결합을 처리한다.

- 최대 8개의 Publisher를 입력할 수 있도록 구현되어 있다.

- `zipWith`의 경우 `Mono`를 반환한다는 특징이 있다.


