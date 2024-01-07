# 검증 - Bean Validation

---

## Bean Validation - 소개

검증 로직을 모든 객체에서 일일이 만들고 다니는 것은 매우 피곤하고 비효율적인 짓이다.

로직 상으로도 사실 null 처리나 range 조회가 대부분이다.

이런 검증 로직을 모든 프로젝트에 적용할 수 있도록 공통화 및 표준화한 것이 Bean Validation.

**애노테이션** 기반으로 검증 로직을 편리하게 적용할 수 있다.

### Hibernate Validator

Bean Validation이라는 기술 표준에 대해 가장 일반적으로 사용하는 **구현체**이다.

Hibernate라는 이름이기는 하지만 ORM과 관련은 없다.

공식 사이트와 매뉴얼이 존재하니 필요할 때 열어보면 된다.

---

## Bean Validation - 시작

### 의존성 추가

`implementation 'org.springframework.boot:spring-boot-starter-validation'`

`build.gradle` 에서 위 의존성을 추가해준다.

스프링부트에서는 자동으로 Hibernate 구현체를 가져와준다.

### 검증 애노테이션 사용

기존에 if문으로 직접 입력했던 모든 로직들을,

`@NotBlank`, `@NotNull`, `@Range(min, max)`, `@Max` 애노테이션으로 대체할 수 있다.

- 각각의 기능은 굳이 설명할 필요가 없이 이름에서 유추가 가능하다.

각 애노테이션에 대해,

- `javax.validation` 으로 시작하면 구현체에 의존하지 않는 **자바 표준 인터페이스**이고,

- `org.hibernate.validator` 로 시작하면 Hibernate 구현체에 의존하는 것.

실무적으로 대부분 Hibernate validator를 사용하기 때문에 자유롭게 쓰자.

### 테스트 코드 작성

Bean Validator를 직접 끌어와서 사용하는 일이 실제 코드에서는 없지만, 테스크 코드에서는 필요하다.

외울 필요는 없으므로 예제 코드 수준만 참고할 것.

---

## Bean Validation - 스프링 적용

### 스프링 MVC의 글로벌 Validator

스프링부트는 `spring-boot-starter-validation` 라이브러리가 있으면, 자동으로 Bean Validator를 인지하여 스프링에 통합한다.

**별도의 글로벌 Validator**를 **직접 등록하지 않은 한**, 스프링MVC는 `LocalValidatorFactoryBean` 이라는 **글로벌 Validator**를 등록하여 사용하기 시작한다.

- 이를 통해 `@Validated` 나 `@NotNull` 같은 **애노테이션 기반 Validation**이 사용된다.

- 지금까지 작성한 validator 클래스를 직접 만들거나, validator로 직접 등록하는 일은 Bean Validation 적용으로 **전부** 대체할 수 있다.

### 검증 순서

- `@ModelAttribute` 각각의 필드에서 타입 변환을 시도함
  
  - 실패 시, `typeMismatch` 로 `FieldError` 추가

- Validator 로직을 적용함.

즉, 애초에 **바인딩에 성공한 필드만** Bean Validation이 적용된다.

- 타입 변환에 실패한 필드에 대해 Bean Validation을 적용해봐야 의미가 없다.

- `typeMismatch` 가 바로 뜨고, validation 로직은 발생하지 않는다.

---

## Bean Validation - 에러 코드

앞서 스프링이 발생시키는 오류코드에 대해, `properties` 등록하여 메시지 처리하는 방법을 확인한 바 있다.

애노테이션 기반 Validation 결과 발생하는 오류코드에 대해서도 동일한 처리가 가능하다.

발생하는 오류코드 이름은 **애노테이션**의 이름과 거의 동일하다.

### Bean Validation 메시지 찾는 순서

- 생성된 메시지 코드 순서대로, `MessageSource` 에서 메시지를 찾음

- 애노테이션에서 `message` 파라미터를 입력한 경우, 이를 사용

- 라이브러리 기본 값 사

---

## Bean Validation - 오브젝트 오류

특정 필드에 대한 validation은 `FieldError`로 처리하며, 이를 애노테이션 기반으로 처리하는 것은 충분히 확인했다.

객체 단위의 validation 처리 또한 여러 가지 방법으로 해볼 수 있다.

### ScriptAssert

클래스 단위에 `@ScriptAssert` 를 달아서, 오브젝트 수준의 validation을 `scripting` 해둘 수 있다.

하지만 해당 기능은 유지보수도 어렵고, 기능적으로 취약점 및 제약사항도 많은 편이므로 그닥 좋은 방법이 아니다.

### 오브젝트 오류는 Java 소스로 처리하자

기능이 있다고 해서 반드시 사용할 필요는 없다.

`ScriptAssert` 는 더 개선되지 않는 한 사용할 이유가 많지 않으므로, 오브젝트 단위의 validation은 기존과 같이 단순 Java 소스 선에서 처리하는 게 보다 낫다.

---

## Bean Validation - 한계

동일한 domain 객체에 대해 **서로 다른 validation 로직**이 적용되는 케이스는 아주 흔하다.

- **insert**에 대한 validation과 **update**에 대한 validation이 동일하면 오히려 이상하다.

- 그렇다고 둘의 domain을 분리해서 사용할 수는 없기 때문에, 하나로 합쳐서 개발하다보면 양자 간 사이드 이펙트가 쉽게 발생한다.

즉, domain 객체에 대해 Bean Validation을 적용하고자 할 시, **validation 로직을 상황에 맞게 커스터마이징할 수 있도록 처리**해줄 필요가 있다.

---

## Bean Validation - groups

동일한 domain 객체에 대해 **등록** 케이스와 **수정** 케이스의 validation을 달리 적용하는 방법을 알아본다.

- groups 기능 적용

- 원본 도메인 대신, **폼 전송을 위한 별도의 모델 객체**를 사용

### groups 기능 적용

Bean Validation은 `groups` 라는 기능을 제공한다.

Validation용 애노테이션에 `groups` 를 명시하면, `@Validated` 를 처리할 때 명시해뒀던 group 에 대해서만 해당 validation을 적용하도록 할 수 있다.

- 예제 코드에서 확인하면 바로 이해가 가능함.

간단히 말해서, 언제 어떤 validation 을 적용할 것인지에 대해 `@Validated` (메서드의 파라미터)와 **도메인 클래스의 각 필드** 선에서 일일이 적어둔 것.

- 단, `@Valid` 애노테이션에서는 사용이 불가하다.

- 실무적으로 사용을 잘 하지 않는데, **다소 복잡하고 관리가 어렵기 때문**

---

## Form 전송 객체 분리

**서비스나 리포지토리에서 사용할 원본 도메인 객체로 클라이언트 input을 직접 받아오는 것은 적절하지 못하다.**

- 조회, 등록, 수정, 삭제에서 **적절한 input값과 validation 내용이 모두 다르고**

- 이에 따라 **개발 시의 영향도**가 지나치게 크며

- 도메인 소스 상 불필요한 **메타 데이터가 증폭될 우려**가 크기 때문.

따라서 도메인을 직접 전달하기보다, 별도의 **폼 전달용 객체**를 만들어서 `@ModelAttribute` 로서 사용하는 것이 적당하다.

- 받기로는 Form 객체로 받고,

- Validation을 처리한 뒤

- Controller에서 도메인 객체를 직접 생성해서,

- Repository에 접근하는 방식.

이렇게 한다면, `groups` 를 굳이 사용할 이유가 없게 된다.

### 예제 코드 요약

- SaveForm 과 UpdateForm 클래스를 분리하고,

- 각각 validation 로직을 커스터마이징해서 집어넣는다.

- 컨트롤러에서 필요에 따라 바인딩하여 사용하고, 성공 로직에서 도메인 객체를 직접 생성한다.

### 참고

Validation 애노테이션에 대해서는 공식 문서를 적극 참고하자.

상상할 수 있는 거의 대부분의 validation 처리가 들어있기 때문에, 잘만 활용하면 정말 효율적으로 코딩할 수 있다.

---

## Bean Validation - HTTP 메시지 컨버터

지금까지 다룬 Bean Validation에 대해 주의할 점은,

`@ModelAttribute`를 기반으로 동작하고 있다는 점.

- 기본적으로 HTTP 요청의 **쿼리 파라미터** 혹은 `HTML Form` 에 대응하는 스펙이다.

- HTTP **Body** 기반으로 동작하는 API 스타일과는 다르다.

- 그래서, **JSON String을 객체로 파싱하는 과정**에서 발생하는 오류에 대해서는 현재까지의 Bean Validation에서 제대로 다루지 못하는 부분이 있다.

### API 요청의 3가지 결과

`@RestController` 를 선언하여 API 스타일로 컨트롤러를 구성할 때, HTTP Body 기반으로 통신을 해보면 세 가지 결과가 나올 수 있다.

- 성공하는 케이스

- 실패하는 케이스 - **JSON에서 객체 생성 실패**

- 실패하는 케이스 - 객체 생성 이후 **Validation**에서 실패

**JSON에서 객체를 생성하는 수준에서 실패가 발생**하면, 

- 애초에 Validation 로직을 동작시킬 필요가 없고, 동작하는 것도 불가하다.
  
  - Controller 진입 자체가 실패한다.

객체 생성이 완료됐다면, 그 이후에는 `@Validated` 애노테이션을 `@ModelAttribute` 에서와 동일하게 활용할 수 있다.

### 검증 오류 결과

`@RequestBody` 로 검증 오류 결과를 단순 리턴해보면, `ObjectError`와 `FieldError`를 있는 그대로 반환해줄 수 있다.

- 인터페이스는 직접 찍어보면 될 것

실무에서는 필요한 데이터를 잘 선별해서, API 스펙을 정의하고 리턴하도록 하자.

### @ModelAttribute와의 차이점

`@ModelAttribute`는 **각각의 필드 단위로** 세밀한 Validation 처리가 발생하기 때문에, 설령 **타입이 맞지 않는** 케이스가 발생하더라도 컨트롤러로 진입시킬 수 있었다.

- 스프링에서 알아서 `FieldError` 처리를 하면서,

- `rejectedValue` 로 넣어주기까지 했었다.

- 컨트롤러 로직을 동작시키는 것도 가능

`HttpMessageConverter`를 사용하는 API 방식의 경우, 각각의 필드 단위에 대한 validation이 발생하기 전에 **전체 객체 단위의 JSON 파싱**이 발생해야하기 때문에 위와 같은 처리가 불가하다.

- 일단 컨버터 작동이 성공해야 `@Validated`가 적용되기 시작하는 것.

- 컨트롤러 진입 자체가 어렵다.

`HttpMessageConverter` 단계에서 실패한 경우에 대한 예외 처리는 이후에 다루도록 한다.


