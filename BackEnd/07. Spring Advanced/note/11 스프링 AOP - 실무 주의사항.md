# 스프링 AOP - 실무 주의사항

---

## 프록시와 내부 호출

### 문제점

스프링은 **프록시 방식의 AOP를 사용**한다. 즉, AOP를 적용하려면 항상 프록시를 통해서 대상 객체를 호출해야 한다.

만약 프록시를 거치지 않고 대상 객체를 직접 호출하게 되면, AOP가 적용되지 않는다. (어드바이스가 호출되지 않는다)

즉, 스프링은 기본적으로 **의존관계 주입 시에** 항상 **프록시 객체**를 주입하고 있다. 그래서 **대상 객체를 직접 호출하는 문제**는 일반적으로 발생할 일이 없는데,

간혹 **대상 객체 내부적으로 메서드 호출이 발생하면** 프록시를 거치지 않고 대상 객체를 직접 호출하는 결과를 낳게 된다.

### 예제 코드

```java
public class CallServiceV0 {

    public void external() {
        log.info("call external");
        internal();
    }
    public void internal() {
        log.info("call internal");
    }
}
    @Before("execution(* hello.aop.internalcall.*.*(..))")
    public void doLog(JoinPoint joinPoint) {
        log.info("aop = {}", joinPoint.getSignature());
    }

    @Autowired
    CallServiceV0 callService;
    @Test
    void external() {
        callService.external();
    }
```

- 위 코드에서 `external`과 `internal` 2개 메서드 모두에 AOP를 거는 것이 목적으로 보이지만,

- 테스트코드를 통해 `external` 을 호출하면, `internal`에서는 AOP가 걸리지 않는다.

자바 언어의 경우 메서드 앞에 별도 참조가 없을 시, `this`라는 뜻으로 자기 자신의 인스턴스를 참조한다.

결과적으로, `internal`의 경우 **대상 객체가 프록시가 아닌 자기 자신을 직접 호출**하게 되고, 어드바이스가 적용되지 않는다.

### 프록시 방식의 AOP 한계

결과적으로 스프링이 사용하는 **프록시 방식 AOP**는 **내부 호출에 프록시를 적용할 수 없는 한계가 있다**.

#### 참고

실제 코드에 AOP를 직접 박아넣는 AspectJ의 경우 이런 문제가 발생하지 않는다.

- 프록시 같은 걸 만드는게 아니라, 바이트코드를 직접 조작해버리기 때문

- 내부 호출과 무관하게 항상 AOP를 적용할 수 있다.

하지만 **로드 타임 위빙** 등을 사용해야 가능한 것으로, 설정이 복잡하고 jvm 옵션 변경의 부담이 있다

AspectJ를 실무에서 직접 사용하는 일은 없다고 보면 된다.

### 대안 1 - 자기 자신 주입

내부 호출을 해결하는 가장 간단한 방법으로, **자기 자신을 의존관계 주입받는** 방식이다.

내부 호출 시, `this` 를 호출하는 게 아닌 **스프링 빈**을 호출함으로써 **프록시 객체 사용을 강제**하고, 결과적으로 AOP를 적용한다.

- 단, 자기 자신을 주입받는 과정이 다소 어렵다. 순환 참조로 인해 애플리케이션 구동을 실패하는 경우가 잦다.

- **수정자 주입**으로 스프링 빈을 세팅해야 그나마 가능하다.

### 대안 2 - 지연 조회

생성자 주입이 실패하는 부분을 해소하기 위해, **지연 조회**를 적용한다.

- `ApplicationContext`나 `ObjectProvider<>`를 주입받고,

- 내부 호출이 필요할 때 **스프링 빈을 조회하여** 호출하는 것

즉, 필요한 객체를 스프링 컨테이너에서 조회하는 시점이 **스프링 빈 생성 시점이 아니라 실제 객체를 사용하는 시점**으로 미루는 것.

### 대안 3 - 구조 변경

가장 나은 대안은 내부 호출이 발생하지 않도록 구조 자체를 변경하는 것이다.

- 내부 호출 대상이 되는 메서드를 다른 클래스로 분리해서 가져가고, 스프링 빈으로 등록한다.

#### 참고

AOP는 주로 트랜잭션 적용이나 로그 출력 기능 같이, **인터페이스에 담길 수준의 메서드** 규모에서 적용한다.

예제처럼 `private` 수준에서 해결될 작은 메서드에서는 AOP를 적용하는 게 더 이상한 것.

실무에서 AOP가 잘 적용되지 않으면 **내부 호출**을 의심해보자.

---

## 프록시 기술과 한계

스프링이 프록시를 만들 때 제공하는 `ProxyFactory`는 `proxyTargetClass` 옵션에 따라 JDK 동적 프록시 혹은 CGLIB를 선택할 수 있다.

- `ture` 일 때 CGLIB이며, 이게 기본값

### JDK 동적 프록시의 한계 - 타입 캐스팅

인터페이스 기반으로 프록시를 생성하는 JDK 동적 프록시는, **구체 클래스로 타입 캐스팅이 불가능한 한계**가 있다.

```java
    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅, 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시를 구현 클래스로 캐스팅, 실패
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }
```

이와 비교되는 CGLIB의 경우, 프록시 객체가 **구체 클래스를 직접 상속받는 관계**에 있기 때문에 이러한 한계에서 자유롭다.

```java
    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅, 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // CGLIB 구현 클래스로 캐스팅, 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
```

이게 왜 중요할까?

- 의존관계 주입 시에 중요하다.

### 의존관계 주입

결론은, JDK 동적 프록시를 사용할 시 **구체 클래스를 사용한 의존관계 주입이 불가능하다**.

- 타입 캐스팅이 실패했던 원리와 동일하다.

- 스프링은 **의존관계 주입 시에 반드시 프록시를 생성해서 주입**하는데, 이를 JDK 동적 프록시로 생성할 시 **구체 클래스와 해당 프록시 간에는 상속관계가 성립하지 않는다**.

```java
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
```

- 위의 `properties`가 false일 때는 JDK 동적 프록시를 강제한다.

- 그 값을 true로 하여, CGLIB 프록시를 사용할 시에만 **구체 클래스를 기반으로 한 프록시 생성**이 가능하다.

#### 정리

물론 인터페이스가 있으면 인터페이스를 기반으로 의존관계 주입을 받는 게 맞다. 위에서 확인된 JDK 동적 프록시의 한계는 실제로 의미가 없는 경우가 대부분이다.

그럼에도 불구하고, 가끔은 **AOP 프록시가 적용된 구체 클래스를 직접 주입받는 일**이 발생할 수밖에 없다.

이럴 때는 CGLIB를 통해 구체 클래스를 기반으로 AOP를 사용하면 된다.

### CGLIB 구체 클래스 기반 프록시 문제점

- 대상 클래스에 **기본 생성자 필수**

- **생성자 2번 호출** 문제

- **final 키워드** 클래스, 메서드에서 사용 불가

#### 대상 클래스에 기본 생성자가 필수

CGLIB는 **구체 클래스를 상속받는 관계**이다. Java 언어 규약 상, 자식 클래스의 생성자는 **부모 클래스의 생성자를 호출하도록 강제**된다.

- `super()` 형태로, 반드시 호출됨

CGLIB 프록시의 생성자 역시 **대상 클래스의 기본 생성자를 호출할 수밖에 없으며**, 대상 클래스에 기본 생성자가 없으면 프록시 생성도 불가능하다.

#### 생성자 2번 호출 문제

프록시 말고 실제 target 객체도 **생성**이 되기 때문에, 결과적으로 target 객체의 생성자는 **2번** 호출될 수밖에 없다.

- 실제 target 생성을 위한 호출

- CGLIB 프록시 객체 생성을 위한 부모 클래스 호출

생성자 자체에 뭔가 유의미한 로직이 있다면 문제가 발생할 소지가 있다.

#### final 키워드 클래스, 메서드 사용 불가

`final` 키워드는 상속과 오버라이딩을 차단한다.

물론, 일반적인 웹 애플리케이션을 개발하는 시점에는 큰 의미 없다.

### 스프링의 해결책

#### 스프링의 기술 선택 변화

##### 스프링 3.2 - CGLIB를 스프링 내부에 함께 패키징

과거에는 CGLIB 라이브러리를 별도로 import했지만, 3.2 버전부터는 아예 패키징에 포함했다.

#### CGLIB 기본 생성자 필수 문제 해결

스프링 4.0부터는 기본 생성자 필수 문제를 해결했다.

`objenesis`라는 특별한 라이브러리를 도입하여, **기본 생성자 없이도 객체 생성을 지원**한다.

#### 생성자 2번 호출 문제 해결

마찬가지로 스프링 4.0부터 `objenesis` 라이브러리를 통해 해결했다.

### 스프링 부트 2.0 - CGLIB 기본 사용

결국 스프링 부트 2.0 부터는 CGLIB를 **기본으로 사용**하고 있다.

- CGLIB 프록시가 가진 문제들을 **전부 해결했기 때문**

즉, 구체 클래스 타입으로 의존관계를 주입하는 것도 자유롭게 사용 가능하며,

지금까지 자주 사용한 그 옵션으로 JDK 동적 프록시로 전환하는 것도 가능하다.

### 정리

개발자 입장에서 보면, 사실 어떤 프록시 기술을 사용하든 별 상관이 없다. 오히려 최대한 모르고 사용하는 것이 좋다.

그런 점에서도 활용성이 보다 넓은 CGLIB를 기본으로 적용하는 것이 좋은 선택이라고 볼 수 있다.


