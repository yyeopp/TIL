# @Aspect AOP

---

## @Aspect 프록시

스프링은 `@Aspect` 애노테이션을 사용해서, **포인트컷과 어드바이스로 구성된 어드바이저 생성 기능**을 지원한다.

- 기존에는 `Advisor`를 직접 만들어서 스프링 빈으로 등록했다면,

- 그 과정을 애노테이션으로 간소화한 것.

애노테이션을 사용해서 **스프링이 프록시 객체를 만들어준다**고 이해하면 된다.

```java
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Around("execution(* hello.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
```

- `@Aspect` : 애노테이션 기반 프록시를 적용하겠다는 선언

- `@Around` : **포인트컷 표현식**을 넣어주는 애노테이션
  
  - `AspectJ` 표현식이 사용된다.
  
  - `@Around` 가 달려있는 **메서드**가 곧 `Advice` 이다.

- `ProceedingJoinPoint` : **프록시를 호출한** 메타데이터

### 동작 원리

**자동 프록시 생성기**는 `Advisor` 빈을 찾아오는 것에 더하여,

`@Aspect` 를 찾아다가 `Advisor` 로 만들고 프록시를 생성하는 기능을 가지고 있다.

즉, 개발자가 `Advisor` 를 직접 생성하는 일을 **애노테이션 기반으로** 대신 수행해서 스프링 빈 등록 및 프록시 생성까지 자동화해준 것.

### @Aspect 가 어드바이저로 변환되는 과정

- 실행 : 스프링 애플리케이션 로딩 시점에, 자동 프록시 생성기가 호출됨

- **모든 `@Aspect` 빈을 조회함** : 컨테이너 내에서 `@Aspect` 애노테이션이 붙은 스프링 빈을 모두 조회한다.

- **어드바이저 생성** : **어드바이저 빌더**를 이용하여, 애노테이션에 적힌 정보를 기반으로 **어드바이저를 생성**한다.

- 어드바이저 저장 : 생성한 어드바이저를 **어드바이저 빌더 내부에** 저장한다.

#### @Aspect 어드바이저 빌더

구현체는 `BeanFactoryAspectAdvisorBuilder`.

`@Aspect`의 정보를 기반으로, **포인트컷, 어드바이스, 어드바이저**를 생성하고 보관하는 것을 담당한다.

- 애노테이션 기반으로 만들어진 어드바이저는 빌더 내부 저장소에 **캐싱**되고,

- 기본적으로 캐시에 저장된 어드바이저가 반환된다.

### 자동 프록시 작동 기작 최종

- 스프링 빈 대상이 되는 객체를 생성 후 빈 후처리기에 전달

- `Advisor` 빈을 조회

- `@Aspect` Advisor 조회 : `@Aspect` 어드바이저 **빌더** 내부에 저장된 `Advisor`를 모두 조회

- 프록시 적용 대상 체크

- 프록시 생성, 빈 등록

결국은 `@Aspect` 로 생성한 어드바이저도 **개발자가 생성한 어드바이저랑 본질적으로 동일**해지는 것이 핵심.

그 이후 포인트컷을 이용한 **적용 대상 여부 조회 로직** 등이 모두 동일하다.

### 정리

실무에서 프록시를 적용할 때는 대부분 `@Aspect`를 사용하여, **애노테이션 기반**으로 적용한다.

보통 이러한 프록시를 적용하는 케이스는 **애플리케이션의 여러 기능 사이에 걸쳐 들어가는** 관심사이다.

이러한 것을 **횡단 관심사(cross-cutting concerns)** 라고 하며,

횡단 관심사를 해결하는 관점에서 **AOP**라는 개념이 발생한다.

- 지금까지 다룬 프록시도 AOP의 일환으로 해석할 수 있다.
