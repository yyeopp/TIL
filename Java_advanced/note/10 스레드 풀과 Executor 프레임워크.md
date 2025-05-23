# 스레드 풀과 Executor 프레임워크

---

## 스레드를 직접 사용할 때의 문제점

### 스레드 생성 비용으로 인한 성능 문제

스레드 생성은 상당히 무거운 작업이다.

- **메모리 할당** : 각 스레드는 자신만의 **콜 스택**을 가지고 있다. 콜 스택은 스레드가 실행되는 동안 사용하는 메모리 공간으로, 이를 할당해줘야 한다.

- **운영체제 자원 사용** : 스레드 생성은 **운영체제 커널 수준**에서 이루어진다. **시스템 콜**을 사용한다는 뜻으로, CPU와 메모리 리소스를 소모한다.

- **운영체제 스케줄러 설정** : 새로운 스레드가 생성되면 운영체제의 스케줄러가 해당 스레드를 관리하고, 실행 순서를 조정한다. 운영체제 스케줄링 알고리즘 자체에 오버헤드를 유발한다.

- 결과적으로 스레드 하나는 보통 **1MB** 이상의 메모리를 사용한다.

즉, 스레드 생성은 단순히 자바 객체 생성하는 것과는 비교할 수 없을 정도로 큰 작업이다.

어떤 작업 하나를 수행할 때마다 스레드를 생성하고 실행한다면, 그 생성 비용이 실제 작업 수행 시간보다도 길 가능성이 크다.

이 문제를 해결하기 위해, 생성한 스레드를 재사용하는 방안을 고려해볼 필요가 있다.

### 스레드 관리 문제

서버의 CPU와 메모리 자원은 한정되어 있으므로 스레드를 무한하게 만들 수는 없다.

- 시스템이 버틸 수 있는 수준까지만 스레드를 생성할 수 있게 관리가 필요하다.

스레드를 적절히 종료하는 것 또한 중요하다.

- 안전한 종료를 위한 작업 중단 처리, 인터럽트 등 신경쓸 부분이 많다.

### Runnable 인터페이스의 불편함

#### 반환 값이 없음

`run()` 메서드는 반환 값이 따로 없다.

즉, 스레드의 실행 결과를 활용하고자 한다면 별도의 메커니즘을 사용해야 한다.

#### 예외 처리

`run()` 메서드는 **체크 예외**를 던질 수 없다.

반드시 메서드 내부에서 처리해야 한다.

---

## 스레드 풀

이상의 문제를 해결하기 위해서는 **스레드를 생성하고 관리하는 풀**이 필요하다.

- 스레드는 기본적으로 스레드 풀에서 관리되며, 유휴 스레드는 스레드 풀에서 쉬고 있다.

- 작업이 필요하면 스레드 풀에서 스레드를 할당받아 실행하며,

- 작업이 완료되면 스레드를 종료하는 것이 아니라 풀에 반납한다.

이러한 스레드 풀을 사용함으로써, 스레드 관련 굉장히 많은 문제들을 해결할 수 있다.

### Executor 프레임워크

당연히 스레드 풀을 직접 구현해야할 필요는 없다.

- 생각보다 할 게 많다.

- 스레드 풀에서 쉬고 있는 스레드는 WAITING 상태로 관리해야 하고, 작업 시 RUNNABLE 로 변경해야 하는 등

- 생산자 소비자 문제도 깊게 생각해야 한다.

자바는 Executor 프레임워크를 이용해, **스레드 풀, 스레드 관리, Runnable의 문제점, 생산자 소비자 문제** 까지 모두 해결하고 있다.

실무에서 스레드를 직접 사용할 일은 거의 없고, 대부분 Executor 프레임워크를 사용한다고 보면 된다.

---

## Executor 프레임워크 소개

자바의 Executor 프레임워크는 **멀티스레딩 및 병렬 처리를 쉽게 사용할 수 있도록 돕는 기능을 모아둔 프레임워크**이다.

작업 실행 관리, 스레드 풀 관리 등을 개발자 대신하여 효율적으로 처리해준다.

### 주요 구성 요소

#### Executor 인터페이스

가장 단순한 **작업 실행 인터페이스**이다.

- `execute(Runnable command)` 메서드 하나만 가지고 있다.

#### ExecutorService 인터페이스

`Executor` 인터페이스를 확장하여, 작업 제출 및 제어 기능을 추가로 제공한다.

주요 메서드는 `submit()`, `close()`

- 기본 구현체는 `ThreadPoolExecutor`이다.

### ThreadPoolExecutor

크게 2가지 요소로 구성된다.

- **스레드 풀**

- **BlockingQueue** : 작업을 보관한다. **생산자 소비자 문제**를 해결하기 위해 `BlockingQueue`를 사용하고 있다.

`execute()` 메서드에 `Runnable` 을 넣어주면, `BlockingQueue`에 해당 작업이 보관되면서 실행된다고 이해하면 된다.

#### 생성자

생성자에서 최소 5개의 필드를 넣어줘야 한다.

- `corePoolSize` : 스레드 풀에서 관리되는 **기본 스레드 수**

- `maximumPoolSize` : 스레드 풀에서 관리되는 **최대 스레드 수**

- `keepAliveTime`, `TimeUnit` : 기본 스레드 수를 초과하여 만들어진 스레드가 **생존 가능한 최대 대기 시간** 이다.

- `BlockingQueue`  : 작업을 보관할 **블로킹 큐**

#### 작업 과정

- **최초의 작업이 들어오면** 그 때 스레드를 만든다.
  
  - 스레드 풀에 미리 만들어두는 건 아니다.

- 작업 요청이 추가로 들어올 때마다 스레드를 추가로 만들다가  `corePoolSize` 크기까지 스레드를 생성한다.
  
  - 해당 스레드 개수 내에서는 스레드를 사용하고 반납하는 방식으로 관리한다.

- 작업 요청은 `BlockingQueue`에서 관리되고, 스레드 풀에 스레드가 들어오는대로 처리된다.

- `close()`를 호출하면 종료되고, 스레드 풀에 대기하는 스레드도 함께 제거된다.

---

## Future

### Runnable과 Callable

#### Runnable

- `run()`의 **반환 타입**은 `void`이다.

- **예외가 선언되어 있지 않다**.
  
  - 해당 인터페이스를 구현하는 모든 메서드가 체크 예외를 던질 수 없다.

#### Callable

- `java.util.concurrent`에서 제공하는 인터페이스이다.

- `call()`은 **반환 타입**이 제네릭 `V` 여서 값을 리턴할 수 있다.

- **예외가 선언**되어 있어서, 모든 예외를 던질 수 있다.

### Callable 적용

```java
 public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new MyCallable());
        Integer result = future.get();
        log("result value = " + result);
        es.close();
    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call()  {
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("create value = " + value);
            log("Callable 종료");
            return value;
        }
    }
```

- `Executors.newFixedThreadPool(size)`를 이용하여 `ExecutorService`를 보다 편리하게 생성할 수 있다.
  
  - core, maximum pool이 1로 설정된 스레드 풀을 가지는 일반적인 `ThreadPoolExecutor` 클래스가 생성된다.

- `Callable`은 **제네릭 타입**을 반환하고 있다.

- `Callable`은 `es.submit()`을 이용해서 호출할 수 있고, `Future` 인터페이스를 이용해서 결과값을 반환받을 수 있다.

### Executor 프레임워크의 강점

요청 스레드가 **결과를 받아야 하는 상황**이라면, `Callable`을 사용하는 방식이 `Runnable`에 비해 훨씬 편리하다.

- 스레드를 생성하거나, `join()` 한다거나 하는 코드가 없다.

- 마치 싱글스레드 방식으로 개발하는 듯한 느낌이다.

단, `future.get()`을 이용해서 결과값을 반환받고자 할 때 2가지 상황이 발생할 수 있다.

- 스레드가 작업을 완료한 경우

- 스레드가 작업을 완료하지 못한 경우

이는 굳이 `Future`라는 인터페이스를 씌워서 결과값을 활용하게 된 사유와 관련이 있다.

### Future 분석

`Future`는 결국 **미래의 결과를 받을 수 있는 객체**라는 뜻이다.

- `Callable`의 `call()` 메서드는 **호출 스레드가 직접 실행하는 것이 아니라, 블로킹 큐에 들어가 있다가 언젠가 실행되는 것**이다.

- 즉, 언제 실행이 완료되어서 결과를 반환 가능하게될 지 전혀 알 수가 없다.

즉, `es.submit()`은 `Callable`의 결과를 반환하는 대신 **결과를 나중에 받을 수 있는 `Future` 객체를 대신 제공**한다.

- `Future`는 내부에 `Callable` 작업의 **완료 여부**와 작업의 **결과 값**을 가지고 있다.

- `submit()` 을 호출한 순간에 `Future`를 생성하고, 그걸 **블로킹 큐**에 담는 것과 동시에 호출한 쪽으로 반환하는 것.
  
  - `Future`의 구현체는 `FutureTask`이다.

- 물론 `Future`에는 결과값이 담겨 있지 않으나, 호출한 스레드 쪽에서는 **블로킹 큐에 담겨있는 Callable 작업에 대한 참조값을 들고 있는 것과 마찬가지**이므로 **미래에 결과 수신**을 약속받을 수 있는 것

- `Future`가 즉시 반환된다는 특징이 있기 때문에, **호출 스레드는 해당 결과 반환을 약속받은 상태에서 그 다음 로직으로 즉시 진행**할 수 있다.

호출된 `Callable`은 별도의 스레드에서 수행된 뒤, `Future`로 결과를 반환한다.

- 정확히는 `Future`의 구현체인 `FutureTask`가 수행되는데,

- 해당 클래스는 `Runnable` 인터페이스도 함께 구현하고 있으며, 결과적으로 `run()` 메서드가 사용된다.

요청 스레드는 `Future`에 대해 `get()` 메서드를 이용해서 결과값을 반환받을 수 있다.

- `Future` 인스턴스의 참조를 가지고 있기 때문에 가능한데,

- `Callable` 작업이 아직 완료되지 않은 상태라면 `Future`가 **완료 상태**가 될 때까지 **대기**하게 된다.

- 이 때 요청 스레드는 자연스럽게 `WAITING` 상태로 전환된다.

- 마치 락을 얻을 때처럼 결과를 얻기 위해 대기해야 하며,

- 이처럼 스레드가 어떤 결과를 얻기 위해 대기하는 것은 **블로킹**이라고 한다.

- `Callable` 작업이 끝나면, `Future` 는 `WAITING` 상태로 들어간 호출 스레드를 **깨워줄 수 있다**.

- 결과적으로 요청 스레드는 `RUNNABLE` 상태로 변하면서 작업 결과물을 활용해나갈 수 있다.

한편, `Callable` 작업을 마친 스레드는 `WAITING` 상태로 전환되며 **스레드 풀**로 돌아간다.

#### 블로킹 메서드

`Future.get()`과 같은 메서드는 **스레드가 작업을 바로 수행하지 못하고 다른 작업의 완료를 기다리게 하는 메서드**이다.

이러한 메서드를 블로킹 메서드라고 부르며,

`Thread.join()`도 좋은 예시이다.

#### 정리

- `Future`는 작업의 미래 결과를 받을 수 있는 객체다.

- `submit()` 호출 시 `Future`가 즉시 반환되어, 요청 스레드는 **블로킹 없이** 필요한 작업을 계속 진행할 수 있다.

- `Future.get()`을 호출하면 작업의 결과를 활용하 수 있는데,
  
  - 작업 처리가 완료될 때까지 요청 스레드가 대기하게 된다.

### Future가 필요한 이유

어차피 `get()`을 호출하면 결과값 반환을 대기하게 되는데, 굳이 복잡하게 `Future`를 먼저 반환하도록 했을까?

`submit()` 자체를 블로킹 메서드로 구현했어도 되지 않을까?

#### Future 없이 결과를 직접 반환하는 경우

`ExecutorService`를 사용한 보람이 없다.

**마치 싱글 스레드로 작업하는 것과 동일한 결과가 나타난다**.

- `Future`로 결과를 우선 반환받고 **그 다음 로직으로 이행**하는 것이 **멀티스레드 로직**의 핵심을 구성하고 있다.

#### Future를 잘못 사용하는 케이스

위와 같은 원리로 인해, `Future`를 호출하자마자 바로 `get()` 을 호출하는 것은 **상당한 비효율을 유발**하며, 멀티스레드의 장점을 전혀 살릴 수 없게 된다.

- 동시 실행 가능한 최대한 많은 수의 작업을 `submit()` 해둔 뒤,

- 결과가 반드시 필요할 때 `get()` 하는 방식으로 구현되어야 한다.

#### 정리

`Future` 라는 개념이 있기 때문에 요청 스레드는 대기하지 않고 다른 작업을 수행할 수 있다.

모든 작업이 끝나고, 본인이 필요할 때 `get()`을 호출하여 최종 결과를 받아낼 수 있다.

애초에 `Future`가 여러 작업을 동시에 요청할 수 있는 원동력이라고도 볼 수 있다.

그런 이유로, `ExecutorService`는 결과를 직접 반환하는 것이 아니라 `Future`를 반환한다.

### Future 인터페이스 주요 메서드

- `cancel()` : 아직 완료되지 않은 작업을 취소한다.
  
  - `interrupt()` 호출 여부도 지정할 수 있다.
    
    - 즉, 호출된 작업이 실제로는 계속 수행 중이지만 `CANCELLED` **상태로 간주**하고 그 값을 돌려받지 않을 수 있다.
  
  - 작업이 취소되었는지 여부를 반환한다.
  
  - **취소된 상태**의 `Future`에 대해 `get()`을 시도하면 예외가 발생한다.
    
    - 결과적으로, `cancel` 을 호출하려면 **해당 작업 결과물을 사용하지 않겠다는 명확한 의사**가 있어야 한다.

- `isCancelled()` : 작업이 취소되었는지 여부를 확인한다.

- `isDone()` : 작업이 완료되었는지 여부를 확인한다.
  
  - 취소되었거나 예외 종료에 대해서도 `true`다.

- `state()` : 상태를 반환한다.
  
  - RUNNING, SUCCESS, FAILED, CANCELLED

- `get()` : 작업이 완료될 때까지 대기하고, 완료되면 결과를 반환한다.
  
  - 호출한 스레드는 작업 완료 시까지 대기한다.
  
  - 타임아웃 매개변수를 넣을 수 있다.

### Future - 예외

`get()` 호출 시, 작업의 결과 뿐만 아니라 작업 과정에서 발생한 **예외**도 받을 수 있다.

- 예외 발생 시, `Future`의 상태는 `FAILED`가 된다.

- `FAILED` 상태가 되면, 작업 스레드는 `ExecutionException` 예외를 던지면서 그 내부에 **작업 과정에서 발생한 원본 예외**를 담는다.

- 호출 스레드 측에서 `ExecutionException`을 받아서 열어보면, **발생했던 원본 예외**를 확인할 수 있는 것이다.

---

## ExecutorService - 작업 컬렉션 처리

`ExecutorService`는 **여러 작업을 한 번에 편리하게 처리하는 기능**을 제공한다.

### 작업 컬렉션 처리

#### invokeAll()

모든 `Callable` 작업을 제출하고, **모든 작업이 완료될 때까지 기다린다.**

- **타임아웃** 옵션을 설정할 수 있다.

```java
        List<CallableTask> tasks = List.of(task1, task2, task3);
        List<Future<Integer>> futures = es.invokeAll(tasks);
```

- 위와 같이, `Future` 의 컬렉션으로 반환받을 수 있다.

- `invokeAll()` 자체가 **블로킹 메서드**이며, 모든 작업이 실행될 때까지 컬렉션이 반환되지 않는다.

#### invokeAny()

모든 작업을 제출하지만, **하나라도 완료되면** 가장 먼저 완료된 작업 결과를 반환하고 **나머지 작업은 취소**한다.

- **타임아웃** 옵션을 설정할 수 있다.

```java
        List<CallableTask> tasks = List.of(task1, task2, task3);
        Integer value = es.invokeAny(tasks);
```

- 결과값을 바로 리턴받을 수 있다.

- `invokeAny()` 자체가 **블로킹 메서드**이다.

---

## ExecutorService 우아한 종료

**서버를 종료하고자 할 때, 진행 중이던 태스크를 정상적으로 마무리한 뒤 종료하는 것**을 **graceful shutdown**이라고 한다.

`ExecutorService` 또한 graceful shutdown의 개념이 적용될 수 있다.

### ExecutorService의 종료 메서드

#### 서비스 종료 메서드

- `void shutdown()`
  
  - 기존 작업을 모두 완료한 뒤 종료한다.
  
  - **논 블로킹 메서드**이다.

- `List<Runnable> shutdownNow()`
  
  - 실행 중인 작업을 중단하고, **대기 중인 작업을 반환**하며 즉시 종료한다.
  
  - 실행 중인 작업 중단에는 **인터럽트**가 사용된다.
  
  - **논 블로킹 메서드**이다.

#### 서비스 상태 확인 메서드

- `boolean isShutdown()`
  
  - 서비스 종료를 확인한다.

- `boolean isTerminated()`
  
  - 셧다운 지시 후 **모든 작업이 완료되었는지** 확읺난다.

#### 작업 완료 대기 메서드

- `boolean awaitTermination()`
  
  - 서비스 종료 시, **모든 작업이 완료될 때까지 대기한다**.
  
  - 타임아웃 적용이 가능하다.
  
  - **블로킹 메서드**이다.

#### close()

**자바 19**부터 지원하는 서비스 종료 메서드이며, 이 메서드는 `shutdown()` 과 기본적으로 동일하다.

- **하루를 기다려도 작업이 완료되지 않을 시** `shutdownNow()` 가 호출된다.

### graceful shutdown 구현

일반적으로, 우아한 종료에는 **타임아웃**을 적용하여 일정 시간이 지나도 진행 중인 작업이 종료되지 않을 시 **강제종료**로 넘어간다.

- `close()` 가 이러한 방향성인데, 타임아웃이 **하루**다.

- 원하는 타임아웃을 적용하려면 **직접 구현해야 한다**.

자바에서도 적절한 graceful shutdown 메서드를 구현하는 쪽으로 공식문서상 권유하고 있다.

대략 아래와 같은 모습으로 구현하면 된다.

```java
 private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown();
        try {
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                List<Runnable> tasks = es.shutdownNow();
                log(tasks.toString());
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException e) {
            es.shutdownNow();
        }
    }
```

- 일단 `shutdown()` 을 호출해서 **새로운 작업을 차단**하고, **큐에 대기 중인 작업만 처리하도록 한다**.
  
  - `shutdown()` 은 **논블로킹**이기 때문에 그 다음 로직으로 넘어간다.

- `shutdown()` 호출 이후 **적당한 타임아웃 처리**를 하기 위해, `awaitTermination()` 을 활용한다.
  
  - 원하는 시간만큼 **블로킹** 상태에서 대기하면서,
  
  - **작업이 모두 종료되는지 여부를 확인한다**.

- 타임아웃이 발생하면, **강제 종료** 페이즈로 진입한다.
  
  - `shutdownNow()`를 호출하여 **인터럽트**를 발생시킨다.

- `shutdownNow()`에 의해서도 종료가 안 되는 작업이 있을 수 있다.
  
  - 인터럽트를 받지 못하도록 설계되어있거나,
    
    - `while(true){}`
  
  - 그 외 치명적인 결함이 존재하는 작업일 것.
  
  - 이 경우 달리 방법이 없으므로 **적당히 기다렸다가** 로그를 잘 남기고 반환한다.

- 또한, **해당 메서드 자체 마저도** **인터럽트**가 들어올 수 있다.
  
  - graceful shutdown 작업 자체도 중단하고 즉시 종료가 필요한 케이스로 상정한다.
  
  - 발생한 `InterruptedException`을 잡아`shutdownNow()`를 호출하는 것으로 처리한다.

#### 정리

서비스 종료 시에도 생각보다 고려할 점이 많다.

기본적으로 **우아한 종료**가 가능하도록 하되, 적당한 **타임아웃**과 **강제 종료**를 고려할 필요가 있다.

---

## Executor 스레드 풀 관리

```java
 public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ExecutorService es = new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);

        printState(es);
        es.execute(new RunnableTask("task1"));
        printState(es, "task1");
        es.execute(new RunnableTask("task2"));
        printState(es, "task2");
        es.execute(new RunnableTask("task3"));
        printState(es, "task3");
        es.execute(new RunnableTask("task4"));
        printState(es, "task4");
        es.execute(new RunnableTask("task5"));
        printState(es, "task5");
        es.execute(new RunnableTask("task6"));
        printState(es, "task6");
        try {
            es.execute(new RunnableTask("task7"));
        } catch (RejectedExecutionException e) {
            log("task7 실행 거절 " + e);
        }

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과");
        printState(es);
        es.close();
        log("== shutdown 완료 ==");
        printState(es);
    }
```

- 기본적으로 **스레드 풀의 사이즈**는 `corePoolSize`를 따른다.
  
  - 위 코드의 경우 2이다.

- **작업 큐**의 사이즈가 지정된 경우가 있을 수 있다.
  
  - 위 코드의 경우, `ArrayBlockingQueue`를 사용하여 큐 사이즈 2로 설정되어 있다.

- `core` 사이즈를 넘어가는 요청이 들어오면, 우선 **작업 큐**에 작업이 보관된다.

- `core` 가 모두 돌고 있는데, **작업 큐마저도 꽉 차있는 상황에서 추가 요청이 들어오면**, 그 때는 **초과 스레드**를 만들어서 작업을 수행하기 시작한다.
  
  - 이 때 초과 스레드를 포함해서 **최대로 생성 가능한 스레드 개수는**
  
  - `maximumPoolSize`에 해당하는 것이다.
  
  - 위 코드에서는 4이다. 추가로 2개를 더 만들 수 있다.

- `maximumPoolSize` 와 작업 큐가 전부 다 채워진 상태에서도 추가로 들어온 요청은, `RejectedExecutionException`을 발생시키며 **거절**한다.

- **초과 스레드**를 사용한 작업이 완료될 시, 지정된 `keepAliveTime` 만큼만 초과 스레드를 유지했다가, **정상 상태로 회귀**하고자 초과 스레드를 **종료**시킨다.
  
  - **초과 스레드**는 **긴급한 상황**에 사용한다는 개념으로,
  
  - 긴급 상황을 모두 정상적으로 처리할 때까지는 초과 스레드가 계속 운용되며
  
  - 긴급 상황이 종료될 시 정상으로 돌아가는 원리이다.

### 정리

- 작업을 요청하면 core 사이즈만큼 스레드를 만든다.

- core 사이즈를 초과하면 큐에 작업을 넣는다.

- 큐를 초과하면 max 사이즈만큼 스레드를 만든다. 임시로 사용되는 **초과 스레드**이다.
  
  - 이 때 큐에도 넣지 않고 초과 스레드가 작업을 바로 수행한다.

- max 사이즈마저 초과하면 요청을 거절하며 예외가 발생한다.

### 스레드 미리 생성하기

기본적으로는 **최초 요청 발생 시** 스레드 풀의 스레드를 생성하는 구조인데, 

이에 따른 **최초 응답 지연**을 방지하기 위해 **기본 스레드를 미리 생성**하는 것도 좋은 방법이다.

- `ExecutorService`에서 지원하는 방법은 아니다.

- `ThreadPoolExecutor`로 캐스팅 이후에 가능하다.
  
  - `prestartAllCoreThreads()`

---

## Executor 전략

### Executor 스레드 풀 관리 - 다양한 전략

`ThreadPoolExecutor`를 사용하면 스레드 풀에 적용되는 다양한 속성을 조절할 수 있다.

이런 속성들을 조절하여 적합한 **스레드 풀 전략**을 사용해야 한다.

이에 대해 자바에서 지원하는 3가지 기본 전략이 있다.

- `newSingleThreadPool()` : 단일 스레드 풀 전략

- `newFixedThreadPool(nThreads)` : 고정 스레드 풀 전략

- `newCachedThreadPool()` : 캐시 스레드 풀 전략

단일 스레드 풀 전략은 **기본 스레드 1개**만 사용하는 것으로, 간단히 테스트 용도로 사용한다고 보면 된다.

### newFixedThreadPool(nThreads) - 고정 풀 전략

`nThreads`만큼의 **기본 스레드**가 생성되고, **초과 스레드는 생성하지 않는다.**

큐 사이즈에 제한을 두지 않는다. (`LinkedBlockingQueue`)

#### 특징

- 스레드 수가 고정되어 있기 때문에,

- CPU가 메모리 리소스 측면에서 예측 가능한 **안정성 지향** 방식이다.

- 큐 사이즈도 제한이 없어서, 이론상 **무한정** 작업을 받아낼 수 있다.

#### 주의

**서버 자원은 여유가 있는데도 사용자 응답이 지연될 수 있다**.

- 실행되는 스레드 수가 고정되어 있기 때문에,

- **사용자 수가 아무리 늘어나도** 큐에만 쌓일 뿐 **처리속도** 자체가 늘어나지는 못한다.

- 사용자 증가가 **갑작스럽거나, 점진적이거나** **양쪽 모두에서** 문제를 유발한다.

### newCachedThreadPool() - 캐시 풀 전략

**기본 스레드를 사용하지 않는다. 60초 생존 주기를 가진 초과 스레드만 사용한다**.

초과 스레드 수는 **제한이 없고**,

**큐에도 작업을 저장하지 않는다**. (`SynchronousQueue`)

- 생산자의 요청은 항상 스레드 풀의 소비자 스레드가 **직접 받아 처리한다는 개념**

즉, **모든 요청은 대기 없이 스레드가 바로 처리할 수 있다**.

#### SynchronousQueue

`BlockingQueue` 인터페이스 구현체인데,

**내부에 저장 공간이 없어서** 생산자의 작업을 소비자 스레드에 **직접 전달**할 수 있다.

달리 말해, **생산자와 소비자가 매칭될 때까지, 둘 중 하나는 대기**하는 방식이다.

- **동기화**라는 이름이 붙은 이유.

#### 특징

**매우 빠르고 유연한 전략**이다.

- CPU, 메모리 자원이 허용하는 한도에서 최대한 빠르게 작업을 처리해줄 수 있다.

- 요청 수에 따라 스레드 수가 증감하기 때문에, 자원 활용 측면에서도 효율적인 면이 있다.

#### 주의

**서버를 터뜨릴 수 있다**.

- 사용자 증가에 따라 CPU, 메모리 사용량이 함께 증가하는 구조이다.

- 특히 **갑작스럽게 요청이 폭증하는 상황**이 발생할 때, **시스템을 다운**시킬 우려가 있다

### 사용자 정의 풀 전략

일반적인 웹 서비스를 운영하는 상황을 생각해봤을 때, 아래와 같이 스레드 풀 전략을 나눠볼 수 있다.

- **일반** : **고정 크기의 스레드**를 사용하여, 예측 가능한 수준의 CPU, 메모리 자원을 소모하며 안정적으로 운영

- **긴급** : 사용자 요청이 갑자기 증가하면, **긴급하게 스레드를 추가로 투입하여** 처리속도를 높인다.

- **거절** : 사용자 요청이 **폭증**하는 상황에 대해 사용자 요청을 거절한다.

**시스템 다운**은 최악의 상황이기 때문에, **폭증**하는 상황에 대해서는 **거절**이 맞는 전략이다.

위 내용을 코드로 옮기면 대략 아래과 같을 수 있다.

```java
ExecutorService es = new ThreadPoolExecutor(
    100, 200, 60, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(1000));
```

- 일반 : 100개의 기본 스레드가 처리한다.

- 긴급 : 큐에 담긴 작업이 1000개를 초과하는 상황으로, 초과 스레드를 100개 더 투입한다.
  
  - Active한 스레드가 2배로 늘어나므로,
  
  - 일반 상황보다도 전체 처리시간은 더 빨라질 수 있다.
  
  - 단, **CPU, 메모리 상황을 정확히 모니터링**할 필요가 있다.

- 거절 : 초과 스레드까지도 투입했는데도 처리가 지연되는 상황으로, 예외를 발생시킨다.
  
  - 고객 서비스라면 `사용자가 많으니 나중에 다시 시도해주세요` 같은 안내가 나갈 것

#### 주의

기본과 최대 스레드를 설정하면서

큐를 `LinkedBlockingQueue`로 사용하는 실수를 할 수가 있다.

이러면 **큐가 가득차는 상황이 발생할 수 없기 때문에** 항상 **기본 스레드**만 사용하게 된다.

---

## Executor 예외 정책

소비자가 처리할 수 없을 정도로 생산 요청이 가득찰 때의 **예외**를 어떻게 처리할 지 정책을 설정해야 한다.

`ThreadPoolExecutor`는 작업을 **거절**하는 다양한 정책을 제공한다.

- **AbortPolicy**: 넘치는 작업은 `RejectedExecutionException`을 발생시키며 거절한다. (기본 정책)
  
  - 개발자는 발생한 Exception을 잡아서 이후 처리를 고민하면 된다.

- **DiscaardPolicy**: 넘치는 작업은 **조용히 버린다**.

- **CallerRunsPolicy**: 넘치는 작업은 **제출한 스레드가 대신해서 작업을 실행**한다. 생산자가 소비자가 된다.
  
  - 생산자가 **작업의 생산이 아닌 소비를 하게 되므로**, **작업 생산 자체가 느려진다**.
  
  - 비동기 호출이 아니라 동기 호출이 이루어지는 듯한 모습
  
  - 생산 **속도를 조절하는** 기작이 될 수 있다.

- **사용자 정의 정책**: 개발자가 직접 정의하여 사용할 수 있다.

### RejectedExecutionHandler

예외 정책은 `ThreadPoolExecutor` 을 생성하는 시점에 입력할 수 있다.

```java
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
```

- 각 `Policy`를 열어보면 `RejectedExecutionHandler`의 구현체에 해당한다.

- **거절** 발생 시, `rejectedExecution` 로직이 발생하고,

- 그 로직을 **어떻게 구현하는가**에 따라 각 정책이 나뉜다고 볼 수 있다.

- **사용자 정의 정책** 또한 해당 인터페이스를 구현함으로써 가능하다.

---

## 정리

### 실무 전략 선택

- **고정 스레드 풀 전략** : 트래픽이 일정하고, 시스템 안전성이 가장 중요

- **캐시 스레드 풀 전략** : 일반적인 성장하는 서비스

- **사용자 정의 풀 전략** : 다양한 상황에 대응

**가장 좋은 최적화는 최적화하지 않는 것이다**.

강의에서 배운 내용을 소화한답시고 필요한 기능을 개발하는 게 아니라 불필요한 최적화에 몰두하는 것은 비효율적인 짓이다.

예측 불가능한 먼 미래에 대비하여 뜬금없는 최적화를 하는 것이 아니라, 시스템 상황을 잘 모니터링하다가 필요한 시점이 됐을 때 개선하는 게 맞다.

시스템 다운만은 무조건 막아야 한다. 시스템 자원을 적절히 활용하되, 최악의 경우 적절히 거절하자.
