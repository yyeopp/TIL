# 스프링 AOP 구현

---

## 시작

```java
@Slf4j
@Aspect
public class AspectV1 {
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
```

- AOP를 적용하려면 `@Aspect` 를 명시해야 한다.
  
  - 해당 애노테이션은 aspectj 패키지에서 지원하고 있다.

- `@Around` 애노테이션에 명시된 `execution` 내용이 바로 **포인트컷**에 해당한다.
  
  - `execution` 은 **AspectJ의 포인트컷 표현식**을 사용하여 명시하게 된다.

- `@Around` 애노테이션이 달린 메서드가 개념적으로 **어드바이스**에 해당한다.

### 참고

- 스프링 AOP는 AspectJ의 **문법**을 차용하여 **프록시 방식의 AOP**를 제공하는 것이지, **AspectJ를 직접 사용하는 것이 아니다**.

- `@Aspect`를 포함하여 스프링 AOP에서 사용하는 기능들은 `aspectjweaver.jar` 라이브러리가 제공하는 기능으로, `spring-boot-starter-aop` 를 통해 의존관계에 묶여있다.

### AOP 적용

`@Aspect`는 애스팩트라는 **표식**일 뿐, **컴포넌트 스캔 대상**이 되는 것은 아니다. 

생성한 Aspect를 AOP로 적용하려면 **스프링 빈**으로 등록해야 하고, 그 방법으로는

- `@Bean` 으로 직접 등록하거나

- `@Component`를 명시하거나

- `@Import`를 이용해 설정 파일에 매핑하면 된다.

---

## 포인트컷 분리

`@Pointcut` 애노테이션을 사용하면, `@Around` 에서 **포인트컷 표현식을 분리**할 수 있다.

### @Pointcut

위와 같은 애노테이션에 **포인트컷 표현식**을 명시하면, 서로 다른 **어드바이스**에서 **포인트컷**을 재사용할 수 있다.

- 메서드 이름과 파라미터를 합치면 **포인트컷 시그니처**라 한다.

- 메서드 반환 타입이 `void`여야 하고, 코드 내용은 비워둔다.

- 접근 제어자가 유효하다. 다른 클래스에서도 포인트컷을 가져다 쓸 수 있다.

여러 개의 포인트컷을 하나의 어드바이스에 적용할 수도 있다.

- `&&, ||, !` 같은 연산자 적용이 가능하다.

### 포인트컷 참조

포인트컷을 **공용**으로 사용하기 위해 별도의 외부 클래스에 모아두는 방법도 괜찮다.

대신 거기에 명시된 포인트컷은 모두 `public` 접근제어자를 사용해야 할 것.

```java
    public class Pointcuts {
        @Pointcut("execution(* hello.aop.order..*(..))")
        public void allOrder() { }
    }

    @Around("hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
```

- 위와 같은 형식으로 외부 클래스의 포인트컷을 **참조**하여 사용할 수 있다.

---

## 어드바이스 순서

**어드바이스는 기본적으로 순서를 보장하지 않는다**.

- 하나의 애스팩트 클래스에 여러 어드바이스를 집어넣고 사용 중이라면, 그들간의 순서를 보장받을 수 없다.

순서를 지정하기 위한 `@Order` 애노테이션이 존재하기는 하는데, **어드바이스 단위가 아니라 클래스 단위로 적용** 가능하다.

- 즉, 순서를 보장하기 위해서는 **애스팩트를 별도의 클래스로 하나씩 분리해야 한다**.

---

## 어드바이스 종류

- `@Around` : 메서드 호출 전후에 수행
  
  - 가장 강력한 어드바이스로, 사실상 나머지 어드바이스의 기능을 포괄하고 있다.
  
  - 나머지 4개는 `@Around` 를 세분화한 성격

- `@Before` : JoinPoint 실행 이전에 실행

- `@AfterReturning` : JoinPoint가 정상 완료된 후 실행

- `@AfterThrowing` : 메서드가 예외를 던지는 경우 실행

- `@After` : JoinPoint가 종료된 후 실행 (`finally`)

### JoinPoint

모든 어드바이스는 `JoinPoint`를 첫번째 파라미터로 사용할 수 있다.

단, `@Around`는 `JoinPoint`의 하위 타입인 `ProceedingJoinPoint`를 사용해야 한다.

- `@Around`의 경우 `proceed()`를 통해 다음 어드바이스나 타겟을 호출하는 기능이 있기 때문.

### 어드바이스 종류 상세

#### @Before

**JoinPoint 실행 전**

`@Around`와 달리, **작업 흐름을 변경할 수 없다**.

메서드 종료 시 자동으로 다음 타겟이 호출된다.

- `proceed()`를 사용하는 것 자체가 불가하다.

#### @AfterReturning

**JoinPoint가 정상 완료된 후**

`returning` 속성으로 메서드 리턴값을 명시할 수 있다.

- 단, 타입이 일치해야 한다. `Object`를 사용하면 안전하다고 볼 수 있다.

`return`이 없기 때문에 `@Around`와 달리 반환되는 객체를 변경할 수는 없는데,

`set` 을 호출하는 등의 로직을 이용해 반환되는 객체에 대한 **변조**는 충분히 가능하다.

#### @AfterThrowing

**메서드 실행이 예외 종료될 때**

`@AfterReturning`과 유사하게 사용된다.

#### @After

**메서드 실행이 종료될 때**

정상, 실패 모두 실행된다.

보통 `finally` 비슷하게 **리소스 해제용**으로 사용한다.

#### @Around

메서드 실행 전후에 실행되는 가장 강력한 어드바이스

JoinPoint의 **실행 및 진행 여부**까지도 결정 가능하다.

그 외에도

- **전달 값 변환, 반환 값 변환, 예외 변환** 등이 모두 가능하다.

- `proceed()`의 경우 **여러 번 실행시킴으로써 재시도 로직**을 구현할 수도 있다.

### 어드바이스 순서

스프링은 5.2.7 버전부터 **동일한 `@Aspect` 안에서 동일한 JoinPoint**에 대한 **우선순위**를 정했다.

- `@Around`, `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`

단 **호출 순서와 리턴 순서는 반대**라는 점에 유의해야 하고,

`@Aspect` 안에 **동일한 종류의 어드바이스가 2개 이상 존재할 시** 순서가 보장되지 않는다.

- `@Aspect`를 분리하고 `@Order`를 적용해야 한다.

#### @Around 외에 다른 어드바이스가 존재하는 이유

- `@Around`의 기능이 너무 강력하기 때문에 발생할 수 있는 개발자의 실수를 방지할 수 있음
  
  - 로그 찍다가 `proceed()` 호출을 깜빡한다면?

- 코드 작성 의도를 명확하게 드러낼 수 있고, 코드가 단순함

#### 좋은 설계는 제약이 있는 것이다

**제약은 실수를 미연에 방지한다.**

제약 덕분에 **역할이 명확**해질 수 있다. 다른 개발자가 코드를 보고 고민하는 일을 줄일 수 있고, 실수도 방지한다.




