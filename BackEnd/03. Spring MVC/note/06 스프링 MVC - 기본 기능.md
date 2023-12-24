# 스프링 MVC - 기본 기능

---

## 요청 매핑

### 매핑 정보

`@RestController`를 선언하면, 메서드의 반환값이 **뷰를 찾는 것**으로 취급되는 것이 아닌 **HTTP 메시지 바디값**으로 인지된다.

- 보다 자세히는 `@ResponseBody` 와 관련이 있음

`@RequestMapping` 으로 원하는 URL을 메서드에 달아주면, 해당 URL로 호출됐을 때 메서드가 실행되도록 **매핑**이 이루어진다.

- 매핑 URL을 **배열** 형태로 다중 등록할 수도 있다.

- URL 맨 끝에 `/` 를 붙이는지 여부도 보정해준다.

### HTTP 메서드 매핑

별도의 HTTP 메서드 지정이 없으면, 모든 HTTP 메서드를 받아들인다.

- 특정 메서드로 한정했다면, 다른 메서드로 호출했을 때 `405` 를 뱉게 된다.

- 메서드 한정 방법은 `@RequestMapping` 에서 파라미터 입력하기 or `@PostMapping` 사용 (원리는 동일)

### PathVariable 사용

URL 경로 상에 변수를 입력하여, `@PathVariable` 로 꺼내쓸 수 있다.

- 최근 HTTP API에서 선호하는 스타일

- `@RequestMapping` 이 계층 관계로 설정 가능하다는 점과 매칭이 잘 된다.

### 추가 매핑

#### 파라미터로 추가 매핑

URL의 쿼리 파라미터까지 매핑 정보로 활용하는 방법.

`params` 옵션을 `@RequestMapping` 에 넣는다.

- 해당 쿼리 파라미터가 없으면 메서드로 진입하지 않는다.

#### 헤더로 추가 매핑

HTTP 요청의 **헤더**까지 매핑 정보로 활용하는 방법.

`headers` 옵션을 넣어서 사용한다.

- 커스텀 헤더가 있는 경우 활용 가능

#### 미디어 타입으로 추가 매핑

헤더의 `Content-Type` 내용을 바탕으로 매핑한다.

`consumes` 옵션으로 사용 가능.

#### Accept 타입으로 추가 매핑

헤더의 `Accept`를 바탕으로 매핑한다.

- HTTP 요청 당시에, 어떤 형식의 리턴값을 요구한다는 정보

`produces` 옵션으로 사용 가능

- 메서드가 지정한 리턴 형식이, 헤더의 `Accept` 부분과 불일치하면 메서드를 호출하지 않는다.

### 예제 코드 요약

- **URL과 HTTP 메서드 매핑**을 적용해서, **REST 형식**을 준수하는 `@RestController` 를 손쉽게 만들어낼 수 있다.

- 동일한 URL이여도 메서드가 다르다면 다른 기능.
  
  - 자원에 대한 식별 / 자원에 대한 동작을 명확히 구분한다는 규칙

---

## HTTP 요청 - 기본, 헤더 조회

스프링MVC의 `@Controller` 메서드는 웬만한 파라미터는 전부 받아낼 수 있다.

- 서블릿의 `HttpServletRequest` 는 물론이고,

- `HttpMethod`, `Locale` 등 스프링 고유의 파라미터도 지원한다.

- `@RequestHeader` 파라미터로 특정 헤더를 조회하는 것도 가능하고,

- `@CookieValue` 로 특정 쿠키를 조회하는 것도 가능하다.

- `MultiValueMap` 으로 모든 헤더를 받아올 수도 있다.

자세한 내용이 필요하면 스프링 공식 docs 에서 확인.

---

## HTTP 요청 파라미터

클라이언트에서 서버로 요청 데이터를 전달하는 3가지 방법

- GET - 쿼리 파라미터

- POST - HTML FORM

- HTTP message body

### 쿼리 파라미터, HTML FORM

스프링 MVC 에서도 서블릿과 마찬가지로,

`HttpServletRequest`의 `getParameter()` 메서드를 사용해서 GET 과 FORM 의 클라이언트 요청을 활용할 수 있다.

### @RequestParam

스프링 MVC가 제공하는 `@RequestParam` 을 사용해서, 쿼리 파라미터나 Form 데이터를 쉽게 사용할 수 있다.

- 실제 파라미터명을 지정해서 가져오는 방법이 있고,

- **파라미터명과 매개변수명을 일치**시키는 방법도 있으며,

- `@RequestParam` 을 아예 생략하는 것마저도 가능하다.
  
  - 단, 생략하려면 특수 클래스가 아닌 일반적인 Java 타입이어야 한다.
  
  - 이것마저 생략하는 건 다소 과한 코드

- `Map<String, Object>` 로 모든 파라미터를 받는 방법도 있다.
  
  - `MultiValueMap` 사용도 드물지만 가능함

#### 필수 파라미터 지정

필수 파라미터 여부를 세팅하여, 메서드 진입 전에 차단하는 방법도 가능하다.

- `@RequestParam(required=true)`

- 기본값이 `true` 다.

`required=true` 일 때, value가 **빈 문자**여도 통과하는 데 지장은 없다.

- 쿼리스트링에서 파라미터 key 만 있고 값이 없어도 통과한다.

`required=false`일 때, 자바 **기본형** 자료형에 `null` 값이 들어가는 경우가 없도록 해야 한다.

- `int` `double` 같은 자료형으로 Optional RequestParam 을 받을 때, 해당 파라미터가 아예 들어오지 않으면,

- 해당 파라미터를 **null** 로 받게되는 결과가 되는데, **기본형 자료는 null 값을 받아낼 수 없다.**

- 이런 케이스에 대비하여 `Integer` 같은 **래퍼 클래스**를 사용해서 받거나, `defaultValue` 옵션을 사용한다.

### @ModelAttribute

파라미터를 하나씩 받아서 원하는 객체에 세팅하는 게 아닌,

원하는 객체를 `@ModelAttribute` 를 적어서 메서드 파라미터로 바로 받아내는 방식이 가능하다.

- 내부적으로 파라미터 get, 객체 set 작업을 자동화한 것

- 파라미터가 존재하지 않거나, 자료형이 다르거나, 집어넣은 객체에 setter 가 없으면 정상적으로 실행될 수 없다.

- 이름만 동일하다면 `@ModelAttribute` 도 생략할 수 있다.
  
  - `@RequestParam` 생략과의 구분 기준은, **자바 단순 타입인지 여부**

---

## HTTP 요청 메시지

HTTP 메시지 **바디** 부분에 입력된 요청 데이터를 서버에서 받는 방법.

### 단순 텍스트

body 값을 받는 방법을 순차적으로 알아보면,

- `HttpServletRequest` 객체에서 `getInputStream()` 하여, `InputStream` 자체를 받아내는 방법
  
  - `StreamUtils` 를 통해 인코딩 형식을 지정하여 String 형으로 받아낼 수 있다.

- `InputStream` 객체를 직접 파라미터에서 받아내는 방법
  
  - 위 방법에서 한 단계를 줄여놓은 것

- `HttpEntity` 를 사용하는 방법
  
  - **스프링 MVC** 에서 본격적으로 지원하는 기능으로,
  
  - HTTP 헤더와 바디를 편리하게 조회 및 **리턴**하는 방법이다.
  
  - 메시지 바디 정보를 직접 반환하기 때문에 view를 조회하는 로직은 아예 들어있지 않고,
  
  - 헤더 정보도 경우에 따라 활용할 수 있다.
  
  - 해당 클래스를 상속받는 `RequestEntity` 나 `ResponseEntity` 도 사용 가능
    
    - 특히 `ResponseEntity` 에서는 HTTP 상태 코드를 간편하게 지정할 수 있어서 좋다.

- `@RequestBody` 를 사용하는 방법
  
  - 보통 `@ResponseBody`와 한 세트로 사용한다.
  
  - HTTP 메시지 바디 정보를 즉시 String 으로 받아낼 수 있고,
  
  - 마찬가지로 리턴 String도 바디에 바로 넣어서 반환할 수 있다.

#### 요청 파라미터 vs HTTP 메시지 바디 in Spring MVC

요청 파라미터를 조회 및 사용하고 싶다?

- `@RequestParam`, `@ModelAttribute`

HTTP 메시지 바디를 조회 및 사용하고 싶다?

- `@RequestBody`

### JSON

보통은 JSON 을 사용해서 통신하게 된다.

`ObjectMapper` 클래스를 상시 활용하게 된다.

- 마찬가지로 `HttpServletRequest` 객체 및 `InputStream` 을 활용해서 String 값으로 요청을 읽어올 수 있다.

- `@RequestBody` 도 사용할 수 있는데,
  
  - 기존처럼 String 으로 받은 후에 JSON 매핑 처리할 수도 있지만,
  
  - `@RequestBody` 선에서 아예 **객체**를 지정해서, 메서드 진입 전에 자바 객체로 파싱해줄 수 있다.
    
    - **HTTP 메시지 컨버터**가 관여함.
  
  - 메시지 바디를 받고자 할 때 파라미터에 객체를 넣으면서, `@RequestBody` 라고 선언하지 않으면 스프링은 이를 `@ModelAttribute` 로 취급한다.
    
    - 즉, `@RequestBody`는 **생략 불가능**이니 주의하자

- `HttpEntity` 를 이용해서 바디값을 받을 수 있고, **제네릭**을 사용해서 특정 객체로 파싱하는 것도 가능하다.

- `@ResponseBody` 를 메서드에 선언하면, 리턴하는 시점에도 **컨버터가 관여하여** 자동으로 객체를 JSON String으로 파싱해서 반환해준다.

---

## 응답 - 정적 리소스, 뷰 템플릿

스프링 서버에서 응답 데이터를 만드는 3가지 방법

- 정적 리소스

- 뷰 템플릿 사용

- HTTP 메시지 사용

### 정적 리소스

스프링부트의 경우 아래 디렉토리에서 정적 리소스를 제공한다.

- `/static/`

- `/public/`

- `/resources/`

- `/META-INF/resources/`

`src/main/resources` 가 **클래스패스**의 시작 경로이고,

`~/static` 에서부터 **정적 리소스**의 시작 경로이다.

- 해당 경로에서부터 웹서버는 파일시스템 구조를 따라 진입할 수 있다.

### 뷰 템플릿

뷰 템플릿을 거쳐서 HTML을 동적으로 생성한다.

스프링부트가 기본으로 제공하는 뷰 템플릿 경로는

`src/main/resources/templates`

뷰 템플릿을 호출하는 3가지 방법이 있다.

- 명시적으로 `ModelAndView` 를 사용해서 View 명을 지정해주는 경우, 뷰 리졸버가 해당 뷰를 찾고 렌더링해준다.

- 메서드가 String을 반환하면서 `@ResponseBody` 가 달려있지 않은 경우, 반환되는 String 의 이름대 **뷰 리졸버**가 실행되어 뷰를 찾고 렌더링

- 메서드가 void를 반환하는 경우, HTTP 메시지 바디를 처리하는 파라미터가 없는 경우 **요청 URL을 참조해서 논리 뷰** 이름으로 사용함.
  
  - 명시성이 너무 떨어져서 권장하지 않음

타임리프를 사용하는 경우, 클래스패스나 기본 경로 등을 `application.properties` 에서 지정해줄 수 있는 점 참고

### HTTP API, 메시지 바디에 직접 입력

정적 리소스나 뷰 템플릿을 사용하지 않고, HTTP 메시지 바디에 데이터를 직접 입력하는 방법.

앞서 나온 내용들과 대부분 겹친다.

- 서블릿 사용하는 방법

- `HttpEntity` 사용하는 방법

- `@ResponseBody` 사용하는 방법
  
  - 클래스 레벨에서 달아주는 방법
  
  - 아예 `@RestController`로 전환하는 방법

- `@ResponseStatus` 사용하는 방법

---

## HTTP 메시지 컨버터

HTTP 메시지 바디에서 직접 읽고 쓰는 경우, HTTP 메시지 컨버터를 사용하는 것이 편리하다.

스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.

- HTTP 요청 : `@RequestBody`, `HttpEntity`

- HTTP 응답 : `@ResponseBody`, `HttpEntity`

인터페이스는 `HttpMessageConverter`

- 요청과 응답 양쪽에서 사용된다.

- `canRead()` `canWrite()` : 컨버터가 해당 클래스와 미디어타입을 지원하는지 여부

- `read()`  `write()` 실제로 메시지를 읽고 쓰는 기능

스프링부트는 다양한 메시지 컨버터를 제공하는데, 대상 클래스 타입과 미디어 타입을 체크하여 사용여부를 결정한다.

- **우선순위**가 존재하여, 전략 패턴이 적용된다.

- `ByteArrayHttpMessageConverter` 

- `StringHttpMessageConverter`

- `MappingJackson2HttpMessageConverter`

컨트롤러에서 **어떻게 구성을 했을 때 어떤 컨버터가 선택될 수 있는지**를 이해한 상태에서 적절한 컨버터를 사용할 수 있어야 한다.

### 상세 흐름

#### 요청 데이터 읽기

컨버터를 사용해서 실제 HTTP 메시지 바디를 읽는 흐름

- HTTP 요청이 들어올 때, 컨트롤러가 `@RequestBody` 혹은 `HttpEntity` 를 사용한다.

- 메시지 컨버터가 `canRead()` 를 호출하여 메시지를 읽을 수 있는지 확인한다.
  
  - 대상 클래스 타입의 지원 여부
  
  - HTTP 요청의 `Content-Type` (미디어 타입) 지원 여부

- `canRead()` 가 true 면, `read()` 를 호출해서 객체를 생성하고 반환한다.

#### 응답 데이터 생성

- 컨버터가 `canWrite()` 를 호출하여 해당 메시지를 쓸 수 있는지 확인한다.
  
  - 대상 클래스 타입을 지원하는가
  
  - HTTP 요청의 `Accept` 미디어 타입을 지원하는가

- true 면, `write()`를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성한다.

---

## 요청 매핑 핸들러 어댑터 구조

HTTP 메시지 컨버터는 Spring MVC 의 어떤 부분에서 동작하고 있는가?

`DispatcherServlet` 이 실제 핸들러를 호출하기 위해 **핸들러 어댑터를 사용하는 시점**에서 컨버터가 동작한다.

- 정확히는, `@RequestMapping` 기반으로 동작하는 `RequestMappingHandlerAdapter` 가 지원한다.

- 잘 생각해보면, 메시지 컨버팅 작업은 **컨트롤러 진입 이전, 반환 이후**에 이루어져야하기 때문에 당연하다.

### RequestMappingHandlerAdapter 동작 방식

#### ArgumentResolver

지금까지 본 바와 같이 애노테이션 기반 컨트롤러는 매우 다양한 파라미터를 유연하게 사용할 수 있다.

- `HttpServlet` 객체부터 `@RequestBody` 같은 애노테이션까지 전부 지원한다.

이는 RequestMappingHandlerAdapter가, 실제 핸들러를 호출하기 전에 `ArgumentResolver` 를 호출하여 **파라미터를 선처리**해주고 있기 때문에 가능하다.

`ArgumentResolver`가 핸들러가 필요로 할 다양한 파라미터를 선처리한 후에, 비로소 어댑터가 핸들러를 호출하게 된다.

스프링은 30개가 넘는 `ArgumentResolver` 구현체를 지원하고 있다.

- 인터페이스는 `HandlerMethodArgumentResolver`

- **수많은 구현체**를 들고 있기 때문에, 그만큼 **다양한 파라미터 형식**을 지원할 수 있는 것이다.
  
  - 각 파라미터 케이스에 대해 **우선순위 기반 루프**를 돌면서 적절한 `ArgumentResolver`를 찾아서 처리한다.

- 물론 핸들러 어댑터와 핸들러는 **인터페이스에 의존**하고 있기 때문에, 기능 확장에도 유연할 수가 있다.

- `ArgumentResolver`를 직접 만들어서 적용하는 것 또한 가능하다.

#### ReturnValueResolver

`ArgumentResolver` 와 마찬가지로, 리턴되는 값에 대해서 후처리를 진행한다.

- 인터페이스는 `HandlerMethodReturnValueHandler`

스프링에서 10개 이상의 `ReturnValueResolver`를 지원하고 있다.

- 대표적인 적용 예시가, 컨트롤러가 String으로 리턴했을 때 뷰 리졸버 처리를 해주는 것.

### HTTP 메시지 컨버터

HTTP 메시지 컨버터는 `ArgumentResolver`와 `ReturnValueResolver` 에서 사용된다.

- 정확히는, `ArgumentResolver` 에서 요청을 처리할 때 HTTP 메시지 바디를 다루는 케이스가 발생했을 때 (`@RequestBody`, `HttpEntity`) **HTTP 메시지 컨버터를 동적으로 사용**하는 방식.

- 응답의 경우도 마찬가지이다.

결과적으로 스프링 MVC의 경우, `@RequestBody`와 `@ResponseBody`가 있을 시 `RequestResponseBodyMethodProcessor` 를 사용해서 `ArgumentResolver`와 `ReturnValueResolver` 를 모두 처리해주고 있다.

- 해당 클래스의 상속 관계 상 둘다 처리 가능하다.

- `HttpEntity`는 `HttpEntityMethodProcessor` 를 사용

### 확장성

종합할 때, **스프링이 HTTP 메시지 바디를 유연하게 처리**할 수 있는 것은 **아래 기능을 모두 인터페이스로 처리하고 있기 때문**이다.

- `HandlerMethodArgumentResolver`

- `HandlerMethodReturnValueHandler`

- `HttpMessageConverter`

혹시나 기능 확장이 필요할 시, `WebMvcConfigurer` 를 상속받아서 스프링 빈으로 등록하면 된다.


