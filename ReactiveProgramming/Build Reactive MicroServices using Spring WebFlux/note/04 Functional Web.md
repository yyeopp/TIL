# Functional Web

---

## Netty

Netty는 Spring WebFlux에서 기본으로 사용하는 **네트워크 애플리케이션 프레임워크**다.

**비동기식 이벤트 기반 네트워킹**을 모토로 하고 있다.

### Netty의 핵심 컴포넌트 및 구

#### Channel

하나 이상의 입출력 작업을 수행할 수 있는, 네트워크 Socket 등에 대해 열린 연결을 **추상화**한 개념

- `ChannelHandler`가 클라이언트 요청을 받아서, byte 데이터를 Java Object로 변환하는 등의 처리를 수행한다.

#### Event loop

I/O 작업을 비동기로 처리하기 위한 네티의 아키텍처

- 각 이벤트 루프가 **단일 스레드**로 실행되며, 지속적으로 **이벤트 큐**를 확인하여 처리한다.
  
  - 정확히는 서버 코어 수만큼 이벤트 루프 스레드가 생성된다.

- 큐에서 가져온 작업을 순차적으로 처리한 뒤, 완료되면 그에 관련된 **콜백**을 호출한다.

#### Event loop Group

여러 이벤트 루프를 관리하는 단위.

- **Boss Event Loop Group**: 클라이언트의 연결을 수락하는 역할

- **Worker Event Loop Grouop**: 실제 I/O 작업을 수행하는 역할

#### Channel과 EventLoop 간의 연결

Channel이 생성되면, 즉시 EventLoop에 등록된다.

EventLoop는 채널에서 발생하는 여러 이벤트를 처리하는 역할을 담당한다.

#### Channel Lifecycle

- ChannelUnregistered
  
  - 채널이 생성은 되었으나, 이벤트 루프에 등록되기 이전

- ChannelRegistered
  
  - 채널이 이벤트 루프에 등록됨

- ChannelActive
  
  - 채널이 활성화되어, 데이터 송수신이 가능함

- ChannelInactive
  
  - 채널과 클라이언트 간 연결이 종료되어, 채널 자체가 폐쇄될 준비를 함

#### CallBack

네티가 이벤트를 처리할 때 사용하는 인터페이스로, 콜백이 트리거되면 `ChannelHandler` 인터페이스에 의해 이벤트를 처리한다.

#### Future

비동기 작업의 결과를 담는 placeholder의 역할을 수행하는 인터페이스.

미래 어떤 시점에 작업이 완료되면 애플리케이션에서 그 결과에 접근할 수 있도록 해준다.

JDK가 기본 제공하는 `java.util.concurrent.Future`를 발전시킨 `ChannelFuture`이라는 인터페이스를 자체 제공한다.

#### Event & Handler

작업의 상태 변화는 **이벤트**에 의해 이루어지고, 이벤트를 기준으로 **핸들러**가 적절한 동작을 트리거한다.

### 요청 처리 흐름

> written by ChatGPT

- **클라이언트가 서버에 연결 요청**:
  
  - `Boss Event Loop`가 클라이언트의 연결 요청을 수락합니다.
  - 수락된 연결은 새로운 `Channel` 객체로 표현되며, 이 채널은 `Worker Event Loop`에 할당됩니다.

- **I/O 작업 처리**:
  
  - `Worker Event Loop`는 할당된 채널의 모든 I/O 작업을 비동기적으로 처리합니다.
  - 클라이언트가 데이터를 보내면, `ChannelInboundHandler`가 데이터를 읽어들입니다.

- **데이터 처리**:
  
  - 데이터를 읽어들인 후, 채널의 파이프라인에 등록된 여러 핸들러가 순차적으로 데이터를 처리합니다.
  - 각 핸들러는 필요한 변환이나 비즈니스 로직을 수행합니다.

- **응답 생성 및 전송**:
  
  - 데이터를 처리한 후, 응답 데이터를 생성합니다.
  - 응답 데이터는 `ChannelOutboundHandler`를 통해 클라이언트에게 전송됩니다.

### 로그 분석

```log
2024-06-16 16:54:20.245 DEBUG 13232 --- [ctor-http-nio-2] r.n.http.server.HttpServerOperations     : [id:46040444, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] New http connection, requesting read
2024-06-16 16:54:20.245 DEBUG 13232 --- [ctor-http-nio-2] reactor.netty.transport.TransportConfig  : [id:46040444, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Initialized pipeline DefaultChannelPipeline{(reactor.left.httpCodec = io.netty.handler.codec.http.HttpServerCodec), (reactor.left.httpTrafficHandler = reactor.netty.http.server.HttpTrafficHandler), (reactor.right.reactiveBridge = reactor.netty.channel.ChannelOperationsHandler)}
2024-06-16 16:54:20.259 DEBUG 13232 --- [ctor-http-nio-2] r.n.http.server.HttpServerOperations     : [id:46040444, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Increasing pending responses, now 1
2024-06-16 16:54:20.265 DEBUG 13232 --- [ctor-http-nio-2] reactor.netty.http.server.HttpServer     : [id:46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Handler is being applied: org.springframework.http.server.reactive.ReactorHttpHandlerAdapter@64b781b1
2024-06-16 16:54:20.273 DEBUG 13232 --- [ctor-http-nio-2] o.s.w.s.adapter.HttpWebHandlerAdapter    : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] HTTP POST "/v1/movieInfos"
2024-06-16 16:54:20.287 DEBUG 13232 --- [ctor-http-nio-2] s.w.r.r.m.a.RequestMappingHandlerMapping : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Mapped to com.reactivespring.controller.MoviesInfoController#addMovieInfo(MovieInfo)
2024-06-16 16:54:20.295 DEBUG 13232 --- [ctor-http-nio-2] .r.m.a.RequestBodyMethodArgumentResolver : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Content-Type:application/json
2024-06-16 16:54:20.325 DEBUG 13232 --- [ctor-http-nio-2] .r.m.a.RequestBodyMethodArgumentResolver : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] 0..1 [com.reactivespring.domain.MovieInfo]
2024-06-16 16:54:20.336 DEBUG 13232 --- [ctor-http-nio-2] reactor.netty.channel.FluxReceive        : [id:46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] FluxReceive{pending=0, cancelled=false, inboundDone=false, inboundError=null}: subscribing inbound receiver
2024-06-16 16:54:20.349 DEBUG 13232 --- [ctor-http-nio-2] o.s.http.codec.json.Jackson2JsonDecoder  : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Decoded [MovieInfo(movieInfoId=1, name=Batman Begins, year=2005, cast=[Christian Bale, Michael Cane], release (truncated)...]
2024-06-16 16:54:20.418 DEBUG 13232 --- [ctor-http-nio-2] o.s.w.r.r.m.a.ResponseBodyResultHandler  : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Using 'application/json' given [*/*] and supported [application/json, application/*+json, application/x-ndjson, text/event-stream]
2024-06-16 16:54:20.418 DEBUG 13232 --- [ctor-http-nio-2] o.s.w.r.r.m.a.ResponseBodyResultHandler  : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] 0..1 [com.reactivespring.domain.MovieInfo]
2024-06-16 16:54:20.425  INFO 13232 --- [ctor-http-nio-2] reactor.Mono.UsingWhen.1                 : onSubscribe(MonoUsingWhen.MonoUsingWhenSubscriber)
2024-06-16 16:54:20.425  INFO 13232 --- [ctor-http-nio-2] reactor.Mono.UsingWhen.1                 : request(unbounded)
2024-06-16 16:54:20.435 DEBUG 13232 --- [ctor-http-nio-2] o.s.d.m.core.ReactiveMongoTemplate       : Saving Document containing fields: [_id, name, year, cast, release_date, _class]
2024-06-16 16:54:20.464  INFO 13232 --- [ntLoopGroup-3-3] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:3, serverValue:55}] to localhost:27017
2024-06-16 16:54:20.467 DEBUG 13232 --- [ntLoopGroup-3-3] org.mongodb.driver.operation             : retryWrites set to true but the server is a standalone server.
2024-06-16 16:54:20.485 DEBUG 13232 --- [ntLoopGroup-3-3] org.mongodb.driver.protocol.command      : Sending command '{"update": "movieInfo", "ordered": true, "$db": "local", "lsid": {"id": {"$binary": {"base64": "gp4ZQlOIRY6onP8GLXSE7A==", "subType": "04"}}}, "updates": [{"q": {"_id": "1"}, "u": {"_id": "1", "name": "Batman Begins", "year": 2005, "cast": ["Christian Bale", "Michael Cane"], "release_date": {"$date": "2005-06-14T15:00:00Z"}, "_class": "com.reactivespring.domain.MovieInfo"}, "upsert": true}]}' with request id 5 to database local on connection [connectionId{localValue:3, serverValue:55}] to server localhost:27017
2024-06-16 16:54:20.487 DEBUG 13232 --- [ntLoopGroup-3-3] org.mongodb.driver.protocol.command      : Execution of command with request id 5 completed successfully in 13.57 ms on connection [connectionId{localValue:3, serverValue:55}] to server localhost:27017
2024-06-16 16:54:20.492  INFO 13232 --- [ntLoopGroup-3-3] reactor.Mono.UsingWhen.1                 : onNext(MovieInfo(movieInfoId=1, name=Batman Begins, year=2005, cast=[Christian Bale, Michael Cane], release_date=2005-06-15))
2024-06-16 16:54:20.494 DEBUG 13232 --- [ntLoopGroup-3-3] o.s.http.codec.json.Jackson2JsonEncoder  : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Encoding [MovieInfo(movieInfoId=1, name=Batman Begins, year=2005, cast=[Christian Bale, Michael Cane], release (truncated)...]
2024-06-16 16:54:20.499  INFO 13232 --- [ntLoopGroup-3-3] reactor.Mono.UsingWhen.1                 : onComplete()
2024-06-16 16:54:20.508 DEBUG 13232 --- [ctor-http-nio-2] r.n.http.server.HttpServerOperations     : [id:46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Decreasing pending responses, now 0
2024-06-16 16:54:20.508 DEBUG 13232 --- [ctor-http-nio-2] o.s.w.s.adapter.HttpWebHandlerAdapter    : [46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Completed 201 CREATED
2024-06-16 16:54:20.510 DEBUG 13232 --- [ctor-http-nio-2] r.n.http.server.HttpServerOperations     : [id:46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Last HTTP response frame
2024-06-16 16:54:20.510 DEBUG 13232 --- [ctor-http-nio-2] r.n.http.server.HttpServerOperations     : [id:46040444-1, L:/127.0.0.1:8080 - R:/127.0.0.1:11798] Last HTTP packet was sent, terminating the channel

```

- 위와 같이 로그를 추출했을 때, **스레드**의 변화에 주목한다.

- 클라이언트에 대한 HTTP 커넥션을 처리하는 부분은 `ctor-http-nio-2` 스레드에서 진행된다.
  
  - DB와 연계된 비즈니스 로직을 처리하는 부분에서는 해당 스레드가 non-block 상태로 해제됨을 확인 가능하다.
  
  - `Channel` 인터페이스가 이를 처리한다고 이해 가능하다.

- 비즈니스 로직 처리는 `ntLoopGroup-3-3` 스레드에서 진행된다.
  
  - 채널에서 등록된 이벤트가 파이프라인을 따라 이벤트 큐에 등록되고,
  
  - 이벤트 루프가 이를 받아서 고유 핸들러로 처리하는 과정이다.
  
  - 로직 처리 중에 서버 스레드 (nio) 가 block되지 않는 것이 핵심

- 비즈니스 로직 처리가 종료되면, 다시 서버 스레드가 반응하여 요청을 마무리한다.
  
  - 이벤트 루프에서 `onComplete()` 이벤트가 발생한 즉시 서버 스레드가 반응하기 시작함을 확인 가능하다.

---

## Functional Web in Spring WebFlux

Functional Web은 Spring WebFlux에서 RESTFUL API 를 구축하는 프로그래밍 모델이다.

### 구성요소

프로그래밍 관련 주요 특징은 아래와 같다.

- 람다 API

- Method References

- Functional Interface

Functional Web 전체를 크게 두 가지 모듈로 추상화하여 구분할 수 있다.

- Router : 클라이언트에 대한 엔드포인트
  
  - `@RequestMapping` 을 생각하면 됨

- Handler : 요청에 대한 로직을 처리

### Functional Web의 장단점

#### 장점

- 하나의 파일로 REST API의 모든 엔드포인트를 구축 관리할 수 있음

- Controller 모델에 비해 코드양이 감소

#### 단점

- Functional programming 자체에 대한 학습이 필요함

- Bean validation 처리에 대해 따로 학습해야 함

- Exception handling 처리도 상이함

