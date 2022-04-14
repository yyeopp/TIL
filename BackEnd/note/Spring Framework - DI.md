# Spring Framework - DI

---

# 서론

### 기존 MVC는?

컨트롤러의 경우,

1. 사용자의 요청(act)을 접수

2. method를 통해 data를 get 해서 dto에 담고 (req.getParameter~), service객체의 business logic을 call, return "path"

3. 그러면 다시 1번에서 redirect 또는 forward 해서 사용자를 보내줌. 물론 주로 정보를 가지고 이동해야 하므로 forward가 활용됨

## Framework를 사용하면?

### 1번의 경우

결과적으로 1번에서는 "메서드를 호출" 하는게 주요 할 일.

1번은 act에 반응하기 위해 수많은 if, else if 로 구성되어 있는데, 사실 해당 부분은 게터세터 만들때처럼 자동으로 만드는 것도 충분히 가능한 부분이다. 단순 반복이고 개발의 차이가 없을 부분.

그래서 1번을 생략하고, act가 직접 메서드를 호출하는 방식으로 변하게 될 것

### 2번의 경우

2번, 메서드에서 data를 get해서 request.getP 해서 DTO를 구성하는 작업도 사실 단순반복이고 개발 상의 차이가 없다. 자동으로 하는 게 충분히 가능

대신, 메서드에서 service 객체 속 메서드를 다시 호출하는 것은 (Business Logic) 직접 하는 것이 맞다.

단, 컨트롤러에서 서비스 객체를 호출할 때는 서비스객체 싱글톤 구성 / 컨트롤러에서 서비스객체 생성 및 호출이라는 코드 작성이 필요한데, 이 또한 자동으로 할 수 있다.

### 3번의 경우

redirect, forward하는 부분 또한 자동으로 가능하다.

### 정리

결론: 컨트롤러에 act에 반응하는 if-else문, request.getParameter하는 부분, 

싱글톤 직접 코딩하기, 다른 객체 new 해서 가져오기 등등도 사라진다.

대신 사전에 개발환경을 설정하는 게 아주 중요해진다.

---



# Spring & 개발환경

## Spring 등장배경

### 용어 정리

#### 자바 빈즈 (JavaBeans)

빌더 형식 개발도구에서 가시적으로 조작이 가능하고 재사용이 가능한 소프트웨어 컴포넌트

자바빈즈 클래스로 작동하기 위해, 객체 클래스는 직렬화되어야 하고, 기본생성자, getter와 setter, 이벤트 처리 메서드들을 포함해야 한다.

#### EJB (Enterprise JavaBeans)

기업환경의 시스템을 구현하기 위한 서버측 컴포넌트 모델. 애플래케이션의 업무 로직을 가지고 있는 서버 애플리케이션

#### POJO (Plain Old Java Object)

오래된 방식의 간단한 자바 오브젝트.

Java EE (EJB) 등의 중량 프레임워크들을 사용하게 됨에 따라, 해당 프레임워크에 종속된 **무거운** 객체를 만들게 된 것에 반발하여 사용하게 된 용어.

즉, 특정 자바 모델이나 기능, 프레임워크 등을 따르지 않은 자바 오브젝트를 지칭한다.

**스프링 프레임워크**가 POJO 방식이다.

### SpringFramework 등장배경

#### EJB의 한계

EJB는 애플리케이션의 작성을 쉽게 해주었다.

- 하지만, 복잡한 스펙으로 인해 개발 효율성이 떨어지고, 테스트를 위해 반드시 EJB 서버가 필요했다. 무료서버로는 지원이 안 된다. 

웹사이트가 점점 커지면서, enterprise 급 서비스가 필요해졌고 이에 EJB가 사용되었으나,

- EJB스펙에 정의된 인터페이스에 따라 코드를 작성해야 하므로 기존에 작성된 POJO를 변경해야 하고

- RMI를 기반으로 하는 서버이므로 Container 자체가 무겁다는 등의 문제가 있다.

이에 EJB를 사용하지 않고 엔터프라이즈 애플리케이션을 개발하는 방법을 찾게 되었다.

#### POJO의 대두

특정 기술에 종속적이지 않기 때문에, 생산성과 이식성이 향상되어 있다.

POJO와 경량 프레임워크를 사용해 EJB가 제공하는 서비스를 구현할 수 있게 되었다. Spring이 그 중 하나.

#### POJO + Framework

EJB서버와 같은 거창한 컨테이너가 필요 없고,

오픈소스이며,

엔터프라이즈급에 필요한 많은 라이브러리가 지원된다.

## Spring Framework?

엔터프라이즈 급 애플리케이션을 만들기 위한 모든 기능을 종합적으로 제공하는, 경량화된 솔루션

JEE(Java Enterprise Edition)가 제공하는 다수의 기능을 지원하고 있어, JEE를 대체하는 프레임워크를 자리잡음

추가적으로 DI(Dependancy Injection)나 AOP(Aspect Oriented Programming)와 같은 기능도 지원

즉, 스프링 프레임워크는 자바로 enterprise application을 만들 때, 포괄적으로 사용하는 programming 및 configuration model을 제공해주는 framework로, **Application** 수준의 인프라 스트럭쳐를 제공.

개발자가 복잡하고 실수하기 쉬운 Low Level에 신경쓰는 대신 Business Logic 개발에 전념할 수 있도록 해주는 도구.

## SpringFramework의 구조

**Spring 삼각형**: POJO를 기반으로, IoC/DI, AOP, PSA

- Enterprise Application 개발 시 복잡함을 해결하는 Spring의 핵심

### PSA

Portable Service Abstraction. 환경과 세부기술의 변경과 관계없이 일관된 방식으로 기술에 접근할 수 있게 해주는 **설계 원칙**

- ex) 데이터베이스에 관계없이 동일하게 적용할 수 있는 트랜잭션 처리방식

### IoC/DI

유연하게 확장가능한 객체를 **만들어두고**, 객체 간의 **의존관계**는 외부에서 다이나믹하게 설정함.

- 필요할 때 new 해서 만들고 사용하는 게 아니라, **일단 다 만들어 두고** 그때그때 Injection해서 사용한다는 뜻

### AOP

Aspect Oriented Programming. 관심사의 분리를 통해서 소프트웨어의 모듈성을 향상. 

**공통 모듈**을 여러 코드에 쉽게 적용 가능.

## SpringFramework의 특징

### 경량컨테이너

스프링은 기본적으로, **자바객체를 담고 있는 컨테이너**다.

스프링 컨테이너는, 이들 자바 객체의 **라이프사이클**을 관리한다.

언제든지 스프링 컨테이너로부터 필요한 객체를 가져와 사용할 수 있다.

### DI 패턴 지원

스프링은 설정 파일(xml)이나 어노테이션을 통해 객체 간 의존 관계를 설정할 수 있다.

객체에서 의존하고 있는 객체를 new로 직접 생성할 필요가 없다.

### AOP 지원

Aspect Oriented Programming. 문제를 바라보는 관점을 기준으로 프로그래밍하는 기법

문제를 해결하기 위한 핵심사항과 전체에 적용되는 공통사항을 기준으로 프로그래밍함으로써, **공통 모듈을 여러 코드에 쉽게 적용**할 수 있게 한다.

자체적으로 프록시 기반 AOP를 지원하므로, **트랜잭션, 로깅, 보안**과 같은 공통기능을 분리해 각 모듈에 적용 가능하다.

### POJO 지원

스프링 컨테이너에 저장되는 자바객체는 특정 인터페이스 구현 혹은 클래스 상속 없이도 사용 가능하다.

- HttpServlet 상속받던 것과 비교하면 될 듯.

### IoC

Inversion of Control. 제어의 반전

Servlet과 EJB의 등장으로 자바 객체의 생성 및 의존관계의 제어권은 Servlet, EJB Container로 넘어갔다.

스프링에서도 객체 라이프사이클을 관리할 수 있는 기능을 제공하는데, 이런 이유로 Spring Container라고 부른다.

### 트랜잭션 처리

JDBC, JTA, 컨테이너가 제공하는 어떤 트랜잭션을 사용해도 설정파일에서 관련정보를 입력하기 때문에, 코드 재활용이 용이하다.

### API 연동

JDBC 비롯, MyBatis, JPA 등 DB 처리를 위한 라이브러리와 폭넓게 연동된다.

## SpringFramework Module

Core를 바탕으로, AOP, ORM, DAO, Web, Context, MVC가 분리되어 있다.

기능을 크게 분류하여 Module로 구현함으로써, 필요한 것을 그때그때 가져다 조립하는 방식으로 쓸 수 있다.

---



# IoC & Container

## IoC

Inversion of Control. 제어의 역행

객체지향 언어에서 object간의 연결 관계를 **런타임에** 결정.

객체 간의 관계가 **느슨하게** 연결됨. (loose coupling)

IoC의 구현 방법 중 하나가 DI인 것.

> 객체 전반에 걸친 모든 제어권이 **애플리케이션**에 있는 것이 아니라, **프레임워크의 컨테이너**에 있게 된다.

> 기존에는,
> 
> 1. 객체 생성
> 
> 2. 의존성 객체 생성 - *클래스 내부에서*
> 
> 3. 의존성 객체 메서드 호출
> 
> 스프링에서는,
> 
> 1. 객체 생성
> 
> 2. 의존성 객체 **주입**.
>    
>    - 스스로 객체를 만드는 것이 아니고, 제어권을 스프링에 위임해 **스프링이 만들어 놓은 객체를** 주입함.
> 
> 3. 의존성 객체 메서드 호출

## IoC의 유형

### Dependency Lookup

컨테이너가 lookup context를 통해서 필요한 resource나 object를 얻는 방식

- Bean에 접근하기 위해 컨테이너가 제공하는 API를 이용하여 Bean을 lookup하는 것

JNDI 이외 방법을 사용한다면 관련 코드를 일일이 변경해줘야 함.

lookup한 object를 casting해줘야 함

### Dependency Injection

Object에 lookup 코드를 사용하지 않고, 컨테이너가 직접 의존 구조를 object에 설정할 수 있도록 지정해주는 방식.

- 클래스 간 의존성을 자신이 아닌 외부 컨테이너에서 주입하게 된다.

lookup 관련 코드들이 object에서 사라짐.

Setter Injection과 Constructor Injection이 있다.

## Container

객체의 생성, 사용, 소멸에 해당하는 라이프사이클을 담당.

라이프사이클을 기본으로 애플리케이션 사용에 필요한 주요 기능을 제공

### 기능

라이프사이클 관리, Dependency 객체 제공, Thread 관리

### 필요성

서비스 객체를 사용하기 위해 각각 Factory 또는 Singleton 패턴을 직접 구현하지 않아도 됨.

비즈니스 로직 외에 부가적인 기능들에 대해서 독립적으로 관리되도록 하기 위함

서비스 lookup이나 configuration에 대한 일관성을 갖기 위함

### IoC Container

오브젝트의 생성과 관계설정, 사용, 제거 등의 작업을 애플리케이션 코드 대신 독립된 컨테이너가 담당

**컨테이너가 코드 대신 오브젝트에 대한 제어권을 갖고 있어**, IoC라고 부름

스프링 컨테이너를 IoC 컨테이너라고 부를 수 있음.

스프링에서 IoC를 담당하는 컨테이너로는, **BeanFactory, ApplicationContext**가 있음.

### Spring DI Container

Spring DI Container가 관리하는 객체를 bean이라 하고, 이 bean의 **생명주기를 관리**하는 의미로 BeanFactory라고 한다.

**Bean Factory에 여러 컨테이너 기능을 추가**하여, ApplicationContext라 한다.

## IoC 개념

### 객체 제어 방식

기존: 필요한 위치에서 개발자가 필요한 객체 생성 로직을 구현

IoC: 객체 생성을 Container에게 위임하여 처리

### IoC 사용에 따른 장점

객체 간의 결합도를 떨어뜨릴 수 있음.

객체 간 결합도가 높으면?

- 해당 클래스가 유지보수될 때 그 클래스와 결합된 다른 클래스도 같이 유지보수 되어야 할 가능성이 높음.
  
  - 없던 매개변수가 추가되는 등의 식. 이걸 막는 약속(인터페이스)이 필요

### 객체 간 강한 결합

기존 MVC 프로젝트를 생각해봤을 때, Controller에서 Service 객체를 사용하고 있는데 Service 객체 프로퍼티나 메서디가 바뀌면 controller까지 싹 바뀌어야 한다.

### 다형성을 통한 결합도 낮추기

**인터페이스를 호출하는 방식**을 활용

구현 클래스 교체가 용이하기 때문에, 다양한 형태로 변화 가능

물론 인터페이스 교체 시 여전히 고칠 것이 많아짐

### Factory를 통한 결합도 낮추기

**팩토리 호출 방식**

- Class가 Factory를 호출하면, 해당 클래스에서 사용하고자 했던 Interface를 구현하고 있는 class를 factory가 생성하는 구조.

**Service를 이용하는 쪽**에서는 Interface만 알고 있으면 구현체가 어떤 건지, 어떻게 생성되는지 알 필요가 없다.

인터페이스 변경 시, 팩토리만 수정하면 된다. 호출 클래스는 수정이 필요하지 않음.

하지만 여전히 클래스는 팩토리에 의존하고 있다는 게 한계

이러한 Factory 패턴은 Container의 기능으로 적용되었고, 그 컨테이너 기능을 제공해주는 게 **IoC 모듈**.

### Assembler를 통한 결합도 낮추기

**IoC를 호출하는 방식**

팩토리 패턴의 장점을 더하여, **어떠한 것에도 의존하지 않는**형태

- Factory 패턴에서 Factory가 Assembler로 대체되는 것 빼고는 구조가 동일한데,

- Class는 Assembler에 **의존성을 주입**할 뿐 Assembler에 **의존**하고 있는 것이 아니다.

**런타임에** 클래스 간 관계가 형성이 됨. 
그걸 해주는 게 Assembler이고, Spring Container가 Assembler의 역할을 할 수 있다.



## Spring DI 용어 정리

### Bean

스프링이 IoC 방식으로 관리하는 오브젝트.

직접 생성과 제어를 담당하는 오브젝트만 bean이라 부른다.

### BeanFactory

스프링이 IoC를 담당하는 핵심 컨테이너.

Bean 등록, 생성, 조회, 반환

### ApplicationContext

BeanFactory를 확장한 IoC 컨테이너.

기존 기능에 스프링이 제공하는 각종 부가 서비스를 추가로 제공한다. 주로 사용된다.

### 설정정보(configuration metadata)

ApplicationContext가 IoC를 적용하기 위해 사용하는 메타정보.

IoC 컨테이너에 의해 관리되는 Bean 객체를 생성하고 구성할 때 사용됨

### 스프링 프레임워크

IoC 컨테이너, ApplicationContext를 포함해서 스프링이 제공하는 모든 기능을 통틀어 말할 때 사용되는 용어

---



# DI (Dependency Injection)


