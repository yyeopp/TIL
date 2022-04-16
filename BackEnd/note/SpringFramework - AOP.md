# SpringFramework - AOP

---

# AOP

**Aspect Oriented Programming**

## 서론

AOP는 기본적으로, **OOP(Object Oriented Programming)을 발전시키기 위한 개념이다.**

기존 단순 OOP는 각 서비스가 각각의 OOP로 프로그래밍 되어 하나의 소프트웨어가 하나의 거대한 OOP로 설계되었는데,

개별 OOP에는 공통적으로 존재하는 로직들이 있어 중복 코드를 양산한다.

AOP는 이러한 공통 로직을 외부로 빼내 **모듈화**하고자 한다.

## 개요

애플리케이션에는 **핵심 관심 사항**과 **공통 관심 사항**이 구분된다.

기본 OOP 에서는 공통관심사항을 여러 모듈에 적용하는 데 있어 중복된 코드를 양상하는 한계가 존재한다.

AOP는 핵심관심사항과 공통관심사항을 **기준으로** 프로그래밍함으로써 **공통모듈**을 쉽게 적용할 수 있게 한다.

핵심 기능에서 분리된 부가기능은, **Aspect**라는 모듈 형태로 설계된다.

**핵심 관심(Core Concern)** 과 **횡단 관심(Crosscut Concern) 혹은 흩어진 관심사** 으로도 표현될 수 있다.

## 적용 예시

메서드 성능 검사: 기존에 `System.currentTimeMilis()`를 메서드 앞뒤에 달았던 것을 개선

**트랜잭션** 처리: 비즈니스 로직 전후에 복잡한 try~catch를 거쳤던 것을 개선

예외반환, 아키텍처 검증

## Spring AOP 용어

### Aspect

흩어진 관심사를 **모듈화**한 것. 주로 부가기능이 모듈화될 것이다.

부가기능을 정의한 코드인 **Advice**와, Advice를 어디에 적용할 것인지 결정하는 **Pointcut**을 합친 개념이 된다.

### Advice

**무엇**(what)을 삽입할 것인지.

실질적인 부가기능을 담고 있는 **구현체**.

### Target

**핵심기능**을 담고 있는 모듈로, 부가기능(Aspect)을 부여할 대상이 된다.

클래스가 될 수도 있고, 메서드가 될 수도 있다.

### JoinPoint

어드바이스가 적용될 수 있는 위치 및 시점을 총칭.

Target 객체를 구현하는 인터페이스의 **모든 메서드**가 Joinpoint가 된다.

함수 실행 전, 실행 후, 반환 후, 예외 발생 시, 실행 전후 등의 시점(when)을 담게 된다.

### Pointcut

Advice를 적용할 Target의 **메서드를 선별하는** **정규표현식**이다.

JoinPoint들은 Pointcut을 통해 **지정**될 수 있다. 구체적으로 Advice가 삽입되고 실행된 지점 및 시점을 지정한다. 

표현식은 execution으로 시작하고, 메서드의 signature를 비교하는 방법이 주로 이용된다.

### Advisor

> Advice + Pointcut

### Weaving

Pointcut에 의해 결정된 Target의 JoinPoint에, Advice를 삽입하는 과정 전반을 뜻한다.

AOP가 핵심기능의 코드에 영향을 주지 않으면서도, 부가기능을 추가할 수 있도록 해주는 핵심적인 과정이다. 

## Pointcut 표현식

표현식을 통해, Joinpoint를 선택한다.

여러 개가 있지만, 중요하게 다룰 것은 `execution()` 종류들

교재의 표를 참고하면 될 것.

`..`으로 **여러 개**를 표현하고, `*`로 **모두**를 표현하는 등의 문법

## Spring AOP의 특징

### 프록시 기반 AOP를 지원

Spring이 타겟 객체에 대한 proxy를 만들어 제공한다.

타겟을 감싸는 프록시는, 런타임에 생성됨

> 프록시란?
> 
> 타겟을 감싸서 타겟에 대한 요청을 대신 받아주는, Wrapping 오브젝트.
> 
> 클라이언트에서 Target을 호출하면, Target이 직접 호출되는 것이 아니고 이를 감싸고 있는 프록시가 호출되어 Target 내의 메서드 실행 전후 처리를 실행시키도록 구성된다.

### 프록시가 호출을 가로챈다(Intercept)

프록시는 타겟 객체에 대한 호출을 가로챈 다음, Advice의 부가기능 로직을 수행하고 난 후 Target의 핵심 기능 로직을 호출한다. (전처리 Advice)

반대로 후처리 Advice도 가능하다.

프록시 객체 자체가 Runtime에 생성된다는 점이 중요한 특징. (동적 Proxy)

### Method JoinPoint만 지원

Spring은 동적 프록시를 기반으로 AOP를 구현하기 때문에, method Joinpoint만을 지원한다.

다시 말해, 핵심기능(Target)의 메서드가 호출되는 **런타임 시점에만** 부가기능(Advice)를 적용할 수 있다.

## Spring AOP 구현 방법

- POJO Class를 이용한 AOP 구현

- Spring API를 이용한 AOP 구현

- Annotation을 이용한 AOP 구현

## Annotation을 이용한 AOP 구현

`@Aspect` 어노테이션을 이용해서, 따로 제작한 Aspect Class에 직접 Advice와 Pointcut 등을 설정하는 방식.

1. 설정 파일에 `<aop:aspectj-autoproxy/>`을 추가함

2. Aspect Class에 `@Aspect`를 달아서 bean으로 등록

3. Class 내에 Advice를 구현
   
   - `@Before` `@After` `@AfterThrowing` `@Around` `@AfterReturning` 같은 어노테이션으로 **PointCut**을 지정
