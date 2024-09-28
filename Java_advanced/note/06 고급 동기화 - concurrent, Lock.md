# 고급 동기화 - concurrent.Lock

---

## LockSupport

`java.util.concurrent` 패키지에서 가장 기본이 되는 건 `LockSupport` 이다.

`LockSupport`를 이용하면 `synchronized`의 가장 큰 단점인 **무한 대기 문제**를 해결할 수 있다.

### LockSupport 기능

스레드를 **WAITING** 상태로 변경한다.

- `WAITING` 상태는 누가 깨워주기 전까지 계속 대기하는 상태로, CPU 실행 스케줄링에 들어가지 않는다.

`LockSupport`의 기능은 아래와 같다.

- `park()` : 스레드를 WAITING 상태로 변경한다.

- `parkNanos(nanos)` : 스레드를 나노초 동안만 **TIMED_WAITING** 상태로 변경한다.
  
  - 타임아웃 이후에는 `RUNNABLE`로 변경된다.

- `unpark(thread)` : WAITING 상태의 대상 스레드를 `RUNNABLE` 로 변경한다.

#### 예제 코드

```java
 public static void main(String[] args) {

        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();

        sleep(100);
        log("Thread-1 state : " + thread1.getState());

//        log("main -> unpark(Thread-1)");
//        LockSupport.unpark(thread1);
        log("main -> interrupt(Thread-1)");
        thread1.interrupt();
    }

    static class ParkTest implements Runnable {
        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();

            log("park 종료, state : " + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }
    }
```

- Thread-1 은 `park()` 를 통해 스스로 `WAITING` 상태에 들어갈 수 있다.

- main은 Thread-1 을 두 가지 방법을 통해 `RUNNABLE`로 되돌려놓을 수 있다.
  
  - `interrupt()` : 인터럽트를 발생시키면, WAITING이 해제된다.
  
  - `unpark(Thread-1)`  : 인터럽트 없이 WAITING을 해제한다.

### 시간 대기

`parkNanos(나노초)` 메서드를 사용하면, `TIMED_WAITING` 상태가 된다.

지정된 시간 이후에는 스레드가 **스스로 깨어난다**.

### BLOCKED vs WAITING

#### 인터럽트

- `BLOCKED`는 **인터럽트가 걸려도 대기 상태에서 빠져나오지 못한다**.

- `WAITING` 상태는 인터럽트가 걸릴 시 **대기 상태를 즉시 빠져나와** RUNNABLE로 변한다.

#### 용도

- `BLOCKED`는 자바의 `synchronized`에서 **락을 획득하기 위해 대기하는 특수한 용도**로 사용된다.

- `WAITING` 상태는 스레드가 특정 조건이나 시간 동안 대기할 때 발생하는 **비교적 일반적**인 상태다.

#### 대기(WAITING)와 시간 대기(TIMED_WAITING)은 서로 짝이 있다.

- `join()` 과 `join(long millis)`

- `park()` 와 `parkNanos(long millis)`

- `wait()` 와 `wait(long timeout)`

두 상태는 모두 **스레드가 대기**하며 **CPU 실행 스케줄링에 들어가지 않는** 비슷한 상태이긴 하지만, 위와 같은 차이점을 가지고 있다.

### LockSupport 정리

`LockSupport`를 잘 사용하면, 스레드를 BLOCKED가 아닌 WAITING 상태로 활용할 수 있다.

- 인터럽트를 이용하여 스레드 무한 대기 현상을 해소할 수 있게되는 것이다.

- 아니면, `parkNanos()`를 이용하여 **자체 타임아웃** (TIMED_WAITING) 상태를 만들어줄 수도 있다.

`synchronized` 대신 `LockSupport`를 활용한 **안전한 임계 영역**을 만들어볼 수 있을 것.

### LockSupport의 한계

`LockSupport`를 `synchronized` 사용하듯이 간편하게 전면적으로 활용하기는 어렵다.

- `synchronized` 에 비해 **기능이 너무 저수준**이라고 보면 된다.

- 스레드 간의 경합, 락 획득 시 다른 스레드에 대한 인터럽트 등등을 직접 구현해야 흉내를 내볼 수 있다.

자바는, `Lock` 인터페이스와 `ReentrantLock` 이라는 구현체로 위와 같은 기능들을 다 구현해두었다.

---

## ReentrantLock

자바는 1.5부터 `Lock` 인터페이스와 `ReentrantLock` 구현체를 제공한다.

### Lock 인터페이스

`Lock` 은 동시성 프로그래밍에서 쓰이는 **안전한 임계 영역을 위한 락을 구현**하는 데 사용된다.

대표적인 구현체로 `ReentrantLock`이 있다.

#### 주의 : 모니터 락이 아님

**모니터 락**과 **BLOCKED**는 모두 `synchronized`에서만 고유하게 사용한다.

`Lock` 인터페이스가 사용하는 **락**은 **객체 내부의 모니터 락**이 아닌 추상화된 개념이다.

### Lock의 메서드

Lock은 다양한 메서드를 제공함으로써 **고수준의 동기화 기법**을 구현하고 있다.

`synchronized` 블록보다도 더 많은 유연성을 제공하며,

특히 **무한 대기 문제**를 깔끔하게 해결할 수 있다.

#### void lock()

**락을 획득한다**. 다른 스레드가 이미 락을 획득했다면, 풀릴 때까지 현재 스레드는 **WAITING** 상태가 된다.

이 메서드는 **인터럽트에 응답하지 않는다**.

- 정확히는, 스레드가 실제로 `WAITING` 이였기 때문에 `RUNNABLE` 전환이 발생하기는 한다.

- `lock()` 메서드는 **인터럽트에 응하지 않는다**는 의도로 만들어졌기 때문에,

- `RUNNABLE`로 물리적으로 전환되더라도 스레드를 **강제로 다시** `WAITING` 으로 변경함으로써 의도한 바를 구현하고 있다.

#### void lockInterruptibly()

락 획득을 시도하되, **다른 스레드가 인터럽트할 수 있도록 한다**.

대기 중에 인터럽트가 발생하면 `InterruptedException`이 발생하며 **락 획득을 포기한다**.

#### boolean tryLock()

락 획득을 시도하여 **즉시 성공 여부를 반환한다**.

획득에 실패하면 `false` 반환과 동시에 락 획득을 포기한다.

#### boolean tryLock(long time, TimeUnit unit)

**주어진 시간 동안** 락 획득을 시도한다.

주어진 시간이 지나도 락을 획득하지 못할 시 `false` 가 반환되며,

대기 중 인터럽트가 발생할 시에도 Exception과 함께 락 획득을 포기한다.

#### void unlock()

**락을 해제한다**.

해제할 시, 락 획득을 대기 중인 다른 스레드가 락을 가져간다.

- 락 획득한 스레드가 호출하지 않으면,

- `IllegalMonitorStateException`이 발생한다.

#### Condition newCondition()

`Condition` 객체를 생성하여 반환한다.

해당 객체는 락과 결합되어 사용하며, 스레드가 특정 조건을 기다리거나 신호를 받을 수 있게 된다.

`Object` 객체의 `wait()`, `notify()`, `notifyAll()` 메서드와 유사하다.

### 공정성

무한 대기 문제는 해결됐는데, **공정성**에 대한 문제를 여전히 해소해야 한다.

`Lock`의 구현체로 `ReentrantLock`이 있는데, 이 클래스는 **스레드가 공정하게 락을 얻을 수 있도록 모드를 제공**한다.

- **선입선출** 가능하도록.

### 비공정 모드 (Non-fair mode)

`ReentrantLock`의 **기본 모드**이다.

락을 먼저 요청했다고 해서 **먼저 획득한다는 보장이 없다**.

특정 스레드가 장기간 락을 획득하지 못할 가능성이 충분히 발생한다.

#### 비공정 모드 특징

- **성능 우선** : 락을 획득하는 속도가 **전체를 봤을 때는 빠르다**

- **선점 가능** : 새로운 스레드가 기존 대기 스레드보다 먼저 락을 획득할 수 있다.

- **기아 현상 가능성** : 특정 스레드는 무한정 락을 획득하지 못할 수도 있다.

### 공정 모드 (Fair mode)

생성자에서 `true`를 전달함으로써 모드를 전환할 수 있다.

공정 모드에서는 **락이 요청한 순서대로 획득된다**.

- 먼저 대기한 스레드가 먼저 락을 획득하여,

- 스레드 간의 **공정성**을 보장한다.

#### 공정 모드 특징

- **공정성 보장**

- **기아 현상 방지** : 모든 스레드가 **언젠가 락을 획득할 수 있도록 보장**된다.

- **성능 저하** : 전체적으로 봤을 때 **락을 획득하는 전체 속도는 느려진다**.

### 정리

`Lock` 인터페이스와 `ReentrantLock` 구현체를 사용하여, `synchronized` 의 단점인 **무한 대기**와 **공정성 문제**를 모두 해결할 수 있다.


