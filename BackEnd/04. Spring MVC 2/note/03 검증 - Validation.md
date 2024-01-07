# 검증 - Validation

---

## 검증 개요

**컨트롤러**의 중요한 역할 중 하나가 **HTTP 요청이 정상인지 여부를 검증하는 것**이다.

### 클라이언트 검증과 서버 검증

**클라이언트 검증**은 고객 사용성이 우수하나, **조작 가능**하므로 보안에 취약하다.

**서버 검증**은 보안이 우수하나, 고객 사용성이 부족해진다.

둘을 적절히 **섞어서 사용**하는 것이 필요하고, **최종적인 서버 검증은 필수**다.

특히 API 방식을 사용할 시, API 스펙 수준에서 **검증 오류를 API 응답 결과에 잘 남겨주는 것**이 중요하다.

---

## 검증 직접 처리

### 예제 코드 요약

- 서버 API에서 다중 if 문으로, **validation을 처리하는 비즈니스 로직**을 개발한다.
  
  - 개별 field에 대한 validation 처리와,
    
    - 타입 검증, 자릿수 검증 등
  
  - 전체 Object에 대한 validation 처리를 의도적으로 구분한다.
    
    - 필드 여러 개 값에 대한 `&&` 연산 

- validation 오류가 존재할 시, `errors` 라는 `Model` 에 `Map` 형태로 담아서 화면으로 리턴한다.
  
  - `errors` 에 대해서는 template에서 받아서 적절하게 표시할 수 있도록 처리

- 오류가 발생할 시 리턴되는`View` 는 **기존 화면을 유지**하도록 함

- 결과적으로, 기존 화면에서 입력 오류가 존재하면, 
  
  - 화면이 이동하지 않는 동시에 
  
  - 발생한 validation 오류들이 화면에 표시된다.
  
  - 클라이언트가 입력한 데이터도 그대로 유지시킨다.

### 문제점

- 뷰 탬플릿 상의 중복이 많음

- **타입 오류**에 대한 처리가 되어있지 않음
  
  - 타입 오류가 발생할 시 **객체 파싱 수준**에서 오류가 발생하면서, 아예 오류 페이지를 띄워버린다.
  
  - 오류 페이지를 띄우지 않았다고 해도, **클라이언트가 입력한 잘못된 값을 화면상에 유지해줄 방법**이 없다.
  
  - 클라이언트가 입력한 값도 어딘가에 별도 관리가 필요하다는 뜻

무엇보다도, 이러한 validation 처리는 모든 애플리케이션에서 다루는 주제이기 때문에, **스프링 수준에서 공통화**가 이루어져있다.

---

## BindingResult

### BindingResult 인터페이스 도입

스프링이 제공하는 `BindingResult` 인터페이스를 적용하여 기존 방식을 개선할 수 있다.

- `Map` 형태로 저장했던 `errors` 가 `BindingResult` 인터페이스로 바뀐다.

- 메서드에서 `BindingResult` 파라미터는 `@ModelAttribute` 파라미터 바로 뒤에 붙어야 한다.

- `addError()` 메서드로 validation 결과를 저장할 수 있다.
  
  - 개별 field에 대한 오류는 `new FieldError()`
  
  - Object 전반에 대한 오류는 `new ObjectError()`
  
  - 파라미터로 필드명, `Model` 명, 기본 메시지 등을 함께 넣어서 만들어야 한다.

- `hasErrors()` 메서드로 validation 통과 여부를 체크하면 되고,

- 별도로 `Model` 을 명시하지 않더라도 `BindingResult`는 자동으로 뷰에 리턴된다.

### @ModelAttribute 파싱 오류 대응

기존 방식에서는, `@ModelAttribute` 객체 파싱을 실패할 시 아예 오류페이지가 리턴된다.

- 400 오류 발생, 오류 페이지 이동

`BindingResult` 를 함께 명시하는 경우, 객체 파싱에 실패하더라도 **컨트롤러를 정상적으로 호출할 수 있다.**

- **파싱 과정에서 어떤 문제가 발생했는지**를, **문제가 발생한 필드에 대한** `FieldError` 로 자동 생성하여

- `BindingResult` 에 `addError()` 처리하고 돌려준다.

`BindingResult` 를 특정 `@ModelAttribute` 바로 뒤에 선언해야 위와 같은 제대로 매핑이 이루어지는 것을 확인 가능하다.

### 문제점

validation 오류가 발생했을 때, 여전히 **클라이언트 입력값을 유지**시켜주는 부분이 누락되어있다.

---

## FieldError, ObjectError

클라이언트가 입력한 값을 validation 오류에도 불구하고 화면상 보존하기 위해, `FieldError` 생성자를 다르게 사용한다.

- 핵심은 `rejectedValue` 를 파라미터로 사용하고,

- 기존 input 값을 `getter` 해와서 파라미터로 입력하는 것.
  
  - 엄밀히 따졌을 때, `@ModelAttribute` 로 입력되는 input 객체는 생성 자체가 되지 않았으므로, 여기서 getter 처리하는 것은 **의미가 없다.**
  
  - 또한 메서드에서 직접 `FieldError` 를 생성하는 것은, **스프링이 이미 객체 파싱 단계에서 생성한 FieldError에 우선순위가 밀려있기 때문에** 별다른 의미가 없다.
  
  - 객체 파싱 오류가 발생한 순간에 이미, 스프링에서 `FieldError`를 새로 생성하고 `rejectedValue` 값도 입력해둔다.
  
  - **화면에서 리턴받는 `BindingResult`에는 스프링이 자동생성한 `FieldError` 가 들어있으며**, 그에 따라 `rejectedValue`도 클라이언트 입력값으로 그대로 보존할 수 있다.

- 그 외에도 `bindingFailure` 파라미터를 `false` 로 설정해준다.
  
  - binding 자체가 실패한 것은 아니고, 데이터타입이 맞지 않은 케이스이기 때문

---

## 오류 코드와 메시지 처리

### 오류 코드 적용

#### FieldError 생성자

`codes`, `arguments` 파라미터를 사용하면, 오류코드로 **메시지**를 찾아서 적용할 수 있다.

- 오류 메시지를 java 소스에서 직접 하드코딩하는 방식을 개선

#### properties 적용

`application.properties` 파일에서 

- `spring.messages.basename=messages,errors`

위와 같이 써놓으면, `errors` 로 시작하는 properties 파일에 대해 `MessageSource` 적용 대상으로 스프링에서 인식해준다.

#### 오류 코드 적용

`codes` 에 오류 코드를 배열 형태로 전달하면, 오류 코드를 순서대로 매칭해서 처음 매칭되는 메시지를 사용한다.

국제화에서 학습한 `MessageSource`를 내부적으로 사용하는 것을 확인 가능하다.

### BindingResult 를 이용한 자동화

`BindingResult`를 메서드 파라미터에서 검증 대상 객체의 바로 뒤에 선언되기 때문에,

굳이 `FieldError` 생성자에서 검증 대상 객체를 일일이 선언해주지 않아도 **어떤 객체를 검증해야하는지** `BindingResult`가 알고 있다고 볼 수 있다.

`rejectValue()` 메서드를 활용해서, `addError()` 메서드를 대체하는 동시에 오류 처리를 보다 자동화할 수 있다.

- `fieldName` 과 `errorCode` 만 입력하는 선에서 오류 코드와 메시지 처리가 가능함.
  
  - 메서드를 타고 들어가보면, `addError()` 처리를 알아서 하고 있다는 것까지 확인 가능
  
  - `target` 에 대한 정보를 생략하게 된다는 점에서 간편해 짐

#### 축약된 오류 코드

`rejectValue()` 를 사용하면, `errors.properties` 에 적시된 오류 코드를 full 로 입력하지 않아도 메시지 처리가 가능하다.

뭔가 네이밍 룰을 준수했을 때 가능한 일로, `MessageCodesResolver` 가 동작하는 원리 파악이 필요하다.

### 오류 코드 설계

오류 코드 또한 설계가 필요하다.

- 단순하게 만들면 범용성이 좋지만, 세밀한 메시지 처리가 불가함

- 너무 자세히 만들면, 범용성이 떨어지고 오류 코드 관리가 어려워짐

스프링은 properties 에서, 객체명과 필드명이 조합된 **보다 세밀한** 메시지 코드가 있으면, **더 높은 우선순위**를 부여한다.

- 예시로 `required` 는 `required.item.itemName` 보다 낮은 우선순위를 가진다.

- `required` 라는 오류코드가 **입력값 누락**에 대한 오류를 **전반적으로 다루는** 역할을 가져간다면,

- 보다 상세한 내용의 `required.item.itemName` 은 `item.itemName` 에 대한 입력값 누락을 다루는 가운데,

- Java 코드 상에서는 `required` 만 적어주는 것으로 위 두 개의 효과를 모두 이끌어낼 수 있다.
  
  - `item.itemName`을 다루는 `rejectValeu()` 소스에서는 `required.item.itemName`을,
  
  - 그 외에는 `required` 를.

스프링은 `MessageCodesResolver` 로 위 기능을 지원한다.

### MessageCodesResolver

`MessageCodesResolver` 인터페이스는 **validation 오류 코드**를 받아서 메시지 코드를 생성한다.

- 구현체는 `DefaultMessageCodesResolver`를 사용

- validation 오류에 대해, target 객체와 필드 정보를 받아서 `FieldError` 혹은 `ObjectError` 를 자동으로 처리하는 로직을 가지고 있다.

#### DefaultMessageCodesResolver의 동작 방식

`resolveMessageCodes()` 메서드를 통해, 

- 오류의 종류

- 객체명

- 필드명

위와 같은 파라미터를 입력해보면 `DefaultMessageCodesResolver` 가 우선순위 기반 **배열**로 **메시지코드**를 출력해내는 모습을 확인할 수 있다.

#### rejectValue() 의 동작 방식

- `rejectValue()` 는 내부적으로 `MessageCodesResolver`를 사용한다.

- `FieldError` 와 `ObjectError` 는 원래부터 **여러 개의 오류코드**를 가지도록 설계되어 있다.

- `MessageCodesResolver`는 여러 개의 오류코드를 **우선순위에 맞게 순서대로** 생성해주고,
  
  - 객체와 필드, 필드, 타입, 오류 성질 순서
  
  - 자세한 내용은 직접 찍어보면 보일 것

- 이걸 바탕으로 `Error` 객체를 생성한 뒤 `rejectValue()` 메서드로 돌려준다.

### 오류 코드 관리 전략

#### 구체적인 것에서 덜 구체적인 것으로

**메시지와 관련된 공통 전략**을 도입하는것이 중요하다.

오류코드에 대해서도 **추상화** 및 **계층 구조**를 도입해야, 관리하기 편리하다.

- 오류코드를 레벨 구조로 구성하고,

- Java 소스에서는 최대한 **로우 레벨 코드**에 의존

- 필요에 따라서 더 높은 레벨의 오류코드를 properties에 정의하고

- 이 때 Java 소스는 수정되지 않더라도 더 높은 레벨의 오류코드를 바탕으로 동작할 수 있음

#### ValidationUtils

Validation 관련하여, `rejectIfEmptyOrWhiteSpace()` 같은 메서드를 제공해주는 클래스이다.

검증 과정에서 `if` 로직을 보다 줄여주는 효과를 낼 수 있다.

#### 정리

- input 객체에 대한 `BindingResult` 매핑

- Validation 처리 결과로 `rejectValue()` 호출

- `MessageCodesResolver` 를 사용해서 검증 오류코드를 **메시지 코드 배열**로 전환

- `new FieldError()` 를 생성하면서, 메시지 코드 배열을 함께 보관

- 화면에서 메시지 코드 **순서대로** 메시지를 찾아서 표시

### 스프링이 직접 만든 오류 메시지 처리

개발자가 직접 설정한 오류코드 외에도, 타입 변환 오류 같이 **스프링이 직접 생성하는 검증 오류코드**가 존재한다.

주로 타입 변환 시 문제가 발생하는데, 직접 찍어보면

- `typeMismatch` 로 시작하는 오류코드 배열이 생성된 것으로 조회된다.

별다른 처리가 없다면 화면 상에는 스프링의 오류메시지가 표시되는데,

`errors.properties`에서 `typeMismatch`를 다루는 순간 개발자가 정의한 오류 메시지를 출력하도록 만들어줄 수 있다.

---

## Validator 분리

컨트롤러에서 validation 로직이 너무 길기 때문에, 검증이라는 기능을 별도의 클래스로 분리하고자 한다.

### Validator 클래스 사용

스프링은 `Validator` 라는 인터페이스를 지원하고 있다.

해당 인터페이스를 직접 구현하고, validation 로직을 이관하는 것으로 컨트롤러를 1차 개선할 수 있다.

- `supports()` 메서드에서는 input 객체 자체에 대한 검증

- `validate()` 메서드에서는 실제 검증 로직을 직접 구현할 수 있다.
  
  - 호출할 때는, input 객체와 `BindingResult` 를 넘겨주면 된다.

오류가 발생할 시 `BindingResult`에 `Errors` 를 생성하여 넣어주기 때문에, 이후 로직에서 동일하게 `hasErrors()` 로 받아서 처리할 수 있다.

### @Validated 적용

사실 `Validator` 인터페이스를 직접 구현할 필요는 없다. 그냥 일반 객체로 만들어서 validation 로직을 옮겨두면, 같은 효과를 낼 수 있다.

하지만 굳이 스프링이 제공하는 인터페이스를 사용하는 이유는, validator 호출하는 부분이 보다 개선될 여지가 있기 때문.

#### WebDataBinder 적용

`WebDataBinder` 는 스프링 파라미터의 바인딩을 처리해주고, **검증 기능**도 내부적으로 포함한다.

컨트롤러에서 `WebDataBinder` 를 선언하면서 validator 클래스를 추가해주면, `@Validated` 에노테이션을 통해 검증기를 자동으로 적용할 수 있다.

- `@InitBinder` 는 해당 컨트롤러에서만 영향을 주는 효과를 발생시키고,

- 글로벌 바인딩은 별도의 설정이 존재한다.

#### @Validated 적용

validator 클래스 호출 로직을 지우고, 대신 검증 대상이 되는 input 객체 파라미터 앞에 `@Validated` 를 붙여줌으로써, validator 호출과 동일한 효과를 발생시킨다.

- `@Validated` 가 붙으면, `WebDataBinder` 에 등록된 검증기를 찾아서 실행하는 효과가 있다.

- `WebDataBinder`에 여러 개의 validator가 등록됐다고 하면 **어떤 검증기를 실행해야 할지** 구분이 필요한데, `supports()` 메서드가 여기서 활용된다.

#### 글로벌 설정

`WebMvcConfigurer` 에서 `getValidator()` 메서드를 오버라이드하면, validator 를 전역으로 설정하는 것도 가능하다.

물론 `@InitBinder` 가 있는 경우 전역보다 지역을 우선시하는 원칙이 적용된다.

- 다만, 글로벌 설정 시 `BeanValidator` 가 자동 등록되지 않는다.


