# SSE - 스트리밍 엔드포인트 구축

---

## SSE

> ChatGPT

SSE (Server-Sent Events)는 웹 기술의 일종으로, 클라이언트가 서버에서 실시간으로 데이터를 수신할 수 있도록 지원합니다. SSE는 HTTP 프로토콜을 통해 서버가 클라이언트에게 지속적으로 데이터를 푸시(push)할 수 있는 방법을 제공합니다. 주로 실시간 데이터 업데이트가 필요한 스트리밍 서비스나 애플리케이션에서 사용됩니다.

### 주요 개념

1. **단방향 통신**: SSE는 서버에서 클라이언트로의 단방향 데이터 전송을 지원합니다. 클라이언트가 서버로 데이터를 보내려면 별도의 HTTP 요청을 사용해야 합니다.

2. **지속적인 연결**: 클라이언트는 서버와의 연결을 유지하면서, 서버는 클라이언트에게 지속적으로 이벤트를 전송할 수 있습니다. 이를 통해 실시간 업데이트를 제공합니다.

3. **텍스트 기반**: SSE는 텍스트 기반으로 데이터를 전송합니다. 서버가 보낸 데이터는 클라이언트 측에서 간단히 처리할 수 있는 텍스트 형식입니다.

4. **재연결**: 클라이언트가 서버와의 연결이 끊어지면, 클라이언트는 자동으로 재연결을 시도합니다. 이 과정에서 클라이언트는 서버의 마지막 상태를 다시 받을 수 있도록 설계되어 있습니다.

### 기본 동작 원리

1. **클라이언트 요청**: 클라이언트는 `EventSource` 객체를 사용하여 서버에 연결 요청을 보냅니다.
2. **서버 응답**: 서버는 `text/event-stream` 콘텐츠 유형으로 응답하며, 지속적인 연결을 유지합니다.
3. **이벤트 전송**: 서버는 특정 형식으로 이벤트를 클라이언트에 전송하며, 클라이언트는 이를 수신하여 처리합니다.

**서버가 폐쇄망 정책을 구성하고 있는 경우**에도, 클라이언트가 서버와 지속적인 연결을 유지하고 SSE(Server-Sent Events)를 통해 데이터를 수신할 수 있습니다. 중요한 것은 연결이 서버에서 클라이언트로 시작되지 않고, 클라이언트가 서버에 요청을 보내고 나서 서버가 클라이언트로 데이터를 푸시하는 방식이라는 점입니다.

### SSE 동작 메커니즘과 방화벽 정책

SSE는 클라이언트에서 서버로의 단일 HTTP 요청을 통해 시작됩니다. 이 요청이 성공하면 서버는 클라이언트와의 연결을 열어두고, `text/event-stream` 형식으로 데이터를 지속적으로 보냅니다. 이는 일반적인 HTTP 트래픽처럼 동작하기 때문에 방화벽 정책에 따라 동작이 가능합니다. 폐쇄망 정책에서도 클라이언트에서 서버로의 아웃바운드 트래픽이 허용되고, 서버에서 클라이언트로의 인바운드 트래픽이 허용되면 문제없이 동작할 수 있습니다.

### 방화벽 정책이 적용된 환경에서의 SSE

1. **클라이언트에서 서버로의 초기 요청**: 클라이언트는 서버로 SSE 요청을 보냅니다. 이 요청은 일반적인 HTTP 요청과 동일하므로, 클라이언트에서 서버로의 아웃바운드 트래픽이 허용되면 이 요청은 성공할 것입니다.

2. **서버의 응답**: 서버는 요청에 응답하여 연결을 열어둡니다. 이때 방화벽이 서버의 응답을 클라이언트로 전달하는 것을 허용해야 합니다. 일반적인 HTTP 응답과 동일한 방식으로 작동하므로, 서버에서 클라이언트로의 인바운드 트래픽이 허용된다면 응답이 전달됩니다.

3. **지속적인 데이터 전송**: 서버는 지속적으로 데이터를 클라이언트로 전송합니다. 이는 초기 HTTP 요청과 연결을 유지하면서 발생하므로, 방화벽 정책에 의해 차단되지 않습니다.

## Sinks

> ChatGPT

`reactor.core.publisher.Sinks`는 Reactor 프로젝트에서 제공하는 도구로, 비동기 이벤트 스트림을 관리하고 조작하는 데 사용됩니다. Reactor는 Reactive Streams 사양을 구현한 라이브러리로, 비동기 및 이벤트 기반 애플리케이션 개발에 유용합니다. `Sinks`는 특히 데이터의 발행과 구독을 제어하고, 발행자의 역할을 간단하게 구현할 수 있도록 합니다.

### 주요 개념과 기능

1. **Sinks의 역할**: `Sinks`는 발행자(publisher)로서 데이터를 발행하고, 구독자(subscriber)가 이를 구독할 수 있게 합니다. 이를 통해 스트림의 생성을 제어하고, 데이터를 유연하게 관리할 수 있습니다.

2. **유형**:
   
   - **Sinks.Many<T>**: 다중 구독자가 데이터 스트림을 구독할 수 있도록 하는 발행자. 예를 들어, 채팅 애플리케이션에서 여러 사용자가 동일한 채팅 메시지를 구독할 때 유용합니다.
   - **Sinks.One<T>**: 단일 값을 발행하는 발행자. 주로 단일 이벤트를 처리할 때 사용됩니다.
   - **Sinks.Empty<T>**: 완료 신호만 발행하는 발행자.

3. **Backpressure 지원**: Reactor는 Reactive Streams 사양을 준수하므로, `Sinks`는 Backpressure를 지원합니다. 이를 통해 구독자가 감당할 수 없는 양의 데이터를 발행하지 않도록 제어할 수 있습니다.

### Sinks.Many<T>

#### ReplaySink

```java
  @Test
    void sink() {
        Sinks.Many<Integer> replaySink = Sinks.many().replay().all();

        replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux1 = replaySink.asFlux();
        integerFlux1.subscribe((i) -> {
            System.out.println("Subscriber 1 : " + i);
        });

        Flux<Integer> integerFlux2 = replaySink.asFlux();
        integerFlux2.subscribe((i) -> {
            System.out.println("Subscriber 2 : " + i);
        });

        replaySink.tryEmitNext(3);
        replaySink.tryEmitNext(4);

        Flux<Integer> integerFlux3 = replaySink.asFlux();
        integerFlux3.subscribe((i) -> {
            System.out.println("Subscriber 3 : " + i);
        });
    }
```

- `Many<T>` 는 `Flux` 를 반환한다. 구독자는 `asFlux()` 를 이용해서 `Flux<T>` 를 반환받게 된다.

`replay`는 새로운 구독자가 구독을 시작할 때, 이전에 발행된 데이터를 재생하여 제공합니다. 이는 과거의 모든 데이터 또는 특정 크기/기간의 데이터를 새로운 구독자에게 재생할 수 있게 합니다.

- **특징**:
  
  - **과거 데이터 재생**: 새로운 구독자가 구독을 시작할 때, 이전에 발행된 데이터를 모두 재생하여 제공합니다.
  - **버퍼 크기 설정 가능**: 재생할 데이터의 크기 또는 기간을 설정할 수 있습니다.
  - **구독 시점과 무관**: 구독 시점과 상관없이 모든 구독자는 동일한 데이터를 받을 수 있습니다.

`replay().all()` 은 모든 구독자가 **이전에 발행된 모든 데이터를 다시 받을 수 있도록** 해준다.

- 마지막에 추가된 3번 구독자가, 1번부터 4번까지 모든 이벤트를 수신할 수 있다.

- `all()` 말고 다른 선택지도 가능하다. (ChatGPT)
  
  - **`limit(int historySize)`**: 지정된 크기만큼의 최신 이벤트만 리플레이합니다.
    
    - 예: `Sinks.many().replay().limit(5)`
    
    - 설명: 최신 5개의 이벤트만 리플레이합니다.
  
  - **`limit(Duration maxAge)`**: 지정된 시간 동안의 이벤트만 리플레이합니다.
    
    - 예: `Sinks.many().replay().limit(Duration.ofSeconds(10))`
    - 설명: 최근 10초 동안 발행된 이벤트만 리플레이합니다.
  
  - **`limit(int historySize, Duration maxAge)`**: 지정된 크기와 시간 동안의 이벤트만 리플레이합니다.
    
    - 예: `Sinks.many().replay().limit(5, Duration.ofSeconds(10))`
    - 설명: 최대 5개의 이벤트 또는 최근 10초 동안 발행된 이벤트 중 먼저 도달하는 조건에 따라 리플레이합니다.

#### multicast

```java
    @Test
    void sinks_multicast() {
        Sinks.Many<Integer> multicast = Sinks.many().multicast().onBackpressureBuffer();

        multicast.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux1 = multicast.asFlux();
        integerFlux1.subscribe((i) -> {
            System.out.println("Subscriber 1 : " + i);
        });

        Flux<Integer> integerFlux2 = multicast.asFlux();
        integerFlux2.subscribe((i) -> {
            System.out.println("Subscriber 2 : " + i);
        });

        multicast.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
    }
```

`multicast`는 여러 구독자에게 실시간으로 데이터를 발행하는 방식입니다. 즉, 발행 시점에 구독하고 있는 구독자들만 데이터를 받을 수 있습니다. 이 방식은 브로드캐스트와 유사합니다.

- **특징**:
  
  - **실시간 데이터 발행**: 데이터가 발행될 때 현재 구독 중인 구독자에게만 데이터를 전달합니다.
  - **이전 데이터 제공 없음**: 구독자가 구독을 시작하기 전에 발행된 데이터는 받을 수 없습니다.
  - **백프레셔 처리**: 구독자가 데이터를 처리하는 속도를 제어할 수 있도록 백프레셔 처리를 지원합니다.

#### unicast

```java
    @Test
    void sinks_unicast() {
        Sinks.Many<Integer> unicast = Sinks.many().unicast().onBackpressureBuffer();

        unicast.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        unicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux1 = unicast.asFlux();
        integerFlux1.subscribe((i) -> {
            System.out.println("Subscriber 1 : " + i);
        });

        Flux<Integer> integerFlux2 = unicast.asFlux();      // EXCEPTION
        integerFlux2.subscribe((i) -> {
            System.out.println("Subscriber 2 : " + i);
        });

        unicast.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
    }
```

`Sinks.many().unicast()`은 여러 값 혹은 데이터 스트림을 발행할 수 있는 발행자입니다. 여러 구독자가 동시에 데이터를 받을 수 있으며, 각 구독자는 독립적으로 데이터를 처리합니다.

- **특징**:
  - 여러 값을 발행할 수 있습니다.
  - 여러 구독자가 데이터를 독립적으로 받을 수 있습니다.
  - 예시: 실시간 스트림 데이터 처리, 이벤트 버스 구현 등에 사용할 수 있습니다.

`Sinks.many().unicast()`는 개념적으로는 여러 구독자가 구독할 수 있지만, 실제로는 각각의 구독자가 독립적인 데이터 스트림을 처리하는 방식으로 동작합니다. 따라서 다수의 구독자가 동시에 데이터를 받는 것은 불가능합니다. 구체적으로 설명하면 다음과 같습니다:

##### Unicast의 동작 방식

- **구독자 독립성**: `unicast`로 생성된 `Sinks.Many`는 여러 구독자가 동시에 데이터를 받을 수 있는 것처럼 보이지만, 실제로는 각각의 구독자는 독립적인 스트림을 처리합니다.

- **구독 시점 데이터 전송**: 각 구독자가 `unicast`로 생성된 `Sinks.Many`를 구독하면, 그 시점부터 발행되는 데이터를 각 구독자에게 전달합니다. 이는 단일 이벤트 스트림을 여러 구독자가 동시에 처리하는 것이 아니라, 각 구독자에게 새로운 이벤트 스트림을 개별적으로 제공하는 것입니다.

- **실제 사용 예시**: 실제로는 주로 여러 구독자가 데이터를 받는 `multicast` 방식이나, 특정 구독자에게만 데이터를 발행하는 `Sinks.One`이나 `Sinks.Empty` 등을 사용하는 것이 일반적입니다. `unicast`는 개념적으로는 다수의 구독자가 가능하나, 실제로는 각 구독자가 독립적으로 처리하는 방식으로 작동합니다.

따라서, 여러 구독자가 동시에 동일한 데이터를 받는 것이 필요한 경우에는 `Sinks.many().multicast()`를 사용하는 것이 더 적합합니다.

`unicast`는 주로 다음과 같은 상황에서 사용될 수 있습니다:

1. **단일 구독자의 데이터 전송**:
   
   - 데이터를 하나의 구독자에게만 전송해야 하는 경우에 적합합니다. 예를 들어, 특정 이벤트가 발생했을 때 해당 이벤트를 하나의 구독자에게만 전달하는 경우에 유용합니다.

2. **요청-응답 형태의 통신**:
   
   - 클라이언트가 서버에게 요청을 보내고, 서버는 그에 대한 단일 응답을 처리해야 할 때 사용될 수 있습니다. 요청에 대한 단일 응답을 처리하는 방식으로 적합합니다.

3. **각 구독자가 독립적인 데이터 스트림 필요 없을 때**:
   
   - 여러 구독자가 독립적인 데이터 스트림을 필요로 하지 않을 때, 즉 각각의 구독자가 동일한 데이터를 동시에 받아야 하는 상황이 아니라면 `unicast`를 사용할 수 있습니다.

### Sinks.One<T>

`Sinks.One`은 단일 값을 발행하는 데 사용됩니다. 이는 단일 구독자에게만 값을 발행할 수 있으며, 한 번 값이 발행되면 완료 상태가 됩니다. 주로 요청-응답 형태의 통신에서 사용될 수 있습니다.

- **특징**:
  - 단일 값 발행: 한 번 값이 발행되면 완료 상태가 됩니다.
  - 구독자는 한 번만 값을 받을 수 있습니다.
  - 예시: HTTP 요청에 대한 단일 응답을 처리할 때 사용할 수 있습니다.

### 이벤트 발행 및 예외 처리

`tryEmitNext` 메서드는 Reactor의 `Sinks` API에서 사용되며, 데이터를 발행할 때 발생할 수 있는 다양한 상황을 더 유연하게 처리할 수 있게 해줍니다. 이 메서드는 발행 시도가 성공했는지 여부를 나타내는 결과를 반환하므로, 발행 실패 시 추가적인 처리를 수행할 수 있습니다.

#### `tryEmitNext` 메서드를 사용하는 이유

1. **유연한 실패 처리**: `tryEmitNext`는 발행이 성공했는지 실패했는지 여부를 나타내는 `Sinks.EmitResult`를 반환합니다. 이를 통해 발행 실패 시 구체적인 대응을 할 수 있습니다.
   
   - 예를 들어, 재시도 로직을 구현하거나, 로깅을 하거나, 특정 실패 상황에 대해 사용자에게 알리는 등의 처리를 할 수 있습니다.

2. **비동기 환경에서의 안정성**: 비동기 애플리케이션에서는 여러 쓰레드가 동시에 데이터를 발행하려 할 수 있습니다. `tryEmitNext`를 사용하면 발행 시도가 실패했을 때, 실패의 원인을 파악하고 적절히 대응할 수 있습니다.

3. **불필요한 예외 방지**: `emitNext`는 발행 실패 시 예외를 던지지만, `tryEmitNext`는 예외를 던지지 않고 결과를 반환합니다. 이를 통해 불필요한 예외 처리를 피하고, 더 깔끔한 코드 작성이 가능합니다.
   
   - `emitNext`에서 `Sinks.EmitFailureHandler.FAIL_FAST`는 **발행 실패 시 즉시 예외를 던지는 방식으로 실패를 처리합니다.**

#### `Sinks.EmitResult` 설명

`Sinks.EmitResult`는 `tryEmitNext` 메서드 호출 결과를 나타내며, 다음과 같은 값을 가질 수 있습니다:

- **OK**: 발행이 성공적으로 완료됨.
- **FAIL_NON_SERIALIZED**: 발행이 실패했으며, 이는 비직렬화된(non-serialized) 접근으로 인해 발생함.
- **FAIL_OVERFLOW**: 발행이 실패했으며, 이는 버퍼 오버플로우로 인해 발생함.
- **FAIL_CANCELLED**: 발행이 실패했으며, 이는 구독이 취소되었기 때문에 발생함.
- **FAIL_TERMINATED**: 발행이 실패했으며, 이는 발행자가 이미 종료되었기 때문에 발생함.

### 백프래셔와 Buffer

Reactor의 `onBackpressureBuffer()` 메서드는 백프레셔 상황에서 데이터를 버퍼에 저장하여 처리하는 방식입니다. 기본적으로 무제한 버퍼를 사용하지만, 버퍼의 크기 및 다양한 설정을 조절할 수 있습니다.

##### 버퍼 크기 및 설정 조절

`onBackpressureBuffer`는 여러 가지 오버로드된 메서드를 제공하여 버퍼 크기, 드롭 전략 등을 설정할 수 있습니다. 주요 설정 방법은 다음과 같습니다:

1. **버퍼 크기 설정**:
   
   - 기본적으로 `onBackpressureBuffer()`는 무제한 버퍼를 사용하지만, 특정 크기의 버퍼를 설정할 수 있습니다.
   - 예를 들어, 버퍼 크기를 10으로 설정하려면 `onBackpressureBuffer(10)`을 사용합니다.

2. **드롭 전략 설정**:
   
   - 버퍼가 가득 찼을 때 데이터를 어떻게 처리할지 결정하는 드롭 전략을 설정할 수 있습니다.
   - 기본적으로 `onBackpressureBuffer()`는 버퍼가 가득 찬 경우 예외를 던집니다.
   - `onBackpressureBuffer(int maxSize, Consumer<? super T> onOverflow)`를 사용하여 버퍼가 가득 찼을 때 특정 동작을 수행할 수 있습니다.

3. **드롭된 데이터 처리 설정**:
   
   - 버퍼가 가득 찼을 때 드롭된 데이터를 처리하는 핸들러를 설정할 수 있습니다.
   - `onBackpressureBuffer(int maxSize, Consumer<? super T> onOverflow, BackpressureOverflowStrategy overflowStrategy)`를 사용하여 특정 드롭 전략과 핸들러를 설정할 수 있습니다.

`BufferOverflowStrategy`는 버퍼가 가득 찼을 때 데이터를 어떻게 처리할지 결정합니다. 주요 전략은 다음과 같습니다:

- **IGNORE**: 기본 전략으로, 버퍼가 가득 차면 예외를 던집니다.
- **ERROR**: 버퍼가 가득 차면 예외를 던집니다.
- **DROP_OLDEST**: 버퍼에 새 데이터를 추가하기 위해 가장 오래된 데이터를 드롭합니다.
- **DROP_LATEST**: 새 데이터를 드롭하고 버퍼는 기존 데이터를 유지합니다.


