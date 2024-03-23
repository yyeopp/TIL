# API 예외 처리

---

## API 예외 처리

오류 상황에서 즉시 HTML 페이지로 리다이렉트하는 경우는 비교적 간단하나,

**API**에서 예외가 발생하는 경우라면 생각할 내용이 더 많다.

각 오류 상황에 맞는 오류 응답의 스펙을 정하고, JSON으로 적절한 데이터를 내려줄 필요가 있다.

### 예제 코드 요약

- `RestController`로 JSON 형태의 req, rsp를 주고 받는 API를 간단히 설계하고 배포한다. 특정 input에 대해서는 의도적으로 `RuntimeException`을 발생시킨다.

- 정상 호출 시, JSON으로 input과 output이 이루어진다.

- 오류 호출 시, 응답값은 기존에 설정했던 대로 **HTML 페이지**가 된다.
  
  - API 클라이언트는 오류가 발생했더라도 **적절한 형태의 JSON**이 리턴되기를 기대할 것이지, HTML 덩어리가 돌아오는 것을 예상하지는 않는다.

- `ErrorPageController` 에서 500 에러를 처리하는 메서드를 추가한다. 
  
  - 단, `produces = MediaType.APPLICATION_JSON_VALUE` 옵션을 기재해서 **Accept-Type: application/json**으로 명시되어 들어오는 API 요청에 대해서는 **해당 메서드를 통해 오류가 처리되도록 유도**한다.
  
  - 실제로 POSTMAN에서 header를 정확히 지정하여 요청을 보내보면, 아까와 같은 HTML 오류가 돌아오는 것이 아닌 **오류 정보가 담긴 JSON**이 **적절한 HTTP status Code**와 함께 돌아오는 것을 확인 가능하다.

---

## 스프링 부트 기본 오류 처리

스프링부트는 API에 대해서도 기본 오류 처리 방식을 제공해주고 있다.

`BasicErrorController`에서 확인 가능하다.

- `errorHtml()` : 클라이언트 요청의 `Accept` 헤더 값을 확ㅇ니하여, `text/html` 일 경우 작동하는 메서드
  
  - 앞서 확인한, 오류페이지 자동 리다이렉트 처리가 여기서 발생한다.

- `error()` : 그 외의 경우에 호출되며, `ResponseEntity`로 HTTP Body에 JSON 데이터를 반환해준다.
  
  - `Accept`를 `application/json`으로 넣고 요청했을 때, 스프링부트가 기본 제공하는 `Map` 형태 JSON 오류 응답을 확인할 수 있다.

### HTML 페이지 vs API 오류

`BasicErrorController`를 확장해서 직접 구현하면 JSON 메시지 자체를 변경하는 것도 가능하다.

다만, `BasicErrorController`에서 HTML 페이지 오류처리를 하는 것은 제법 효율적인 편이지만 API의 경우 예외 처리를 일원화하는 것이 다소 부적절할 수 있다.

- 각각의 컨트롤러나 예외마다 서로 다른 응답 결과를 출력해야 하는 경우가 대부분.

- 좀더 세밀하고 정확한 API 오류처리를 위해서는 별도의 방법을 사용하는 것이 좋다.

결론적으로, `BasicErrorController`의 경우 HTML 페이지로 오류처리하는 케이스에 한정하여 사용하는 것이 좋다.

---

## API 예외 처리 - HandlerExceptionResolver

### 목표

예외가 발생하여 WAS까지 전달되면 기본적으로 HTTP 상태코드는 500으로 처리된다. 이걸, 발생하는 예외 종류에 따라 400이나 404로도 처리하고 싶다.

또한 오류 메시지와 형식 등을 API마다 다르게 처리하고 싶다.

#### 상태코드 변환

특정 예외에 대해 HTTP 상태코드를 400으로 처리하고 싶다면?

- 예시로, `IllegalArgumenException`을 400으로 처리하기

### HnadlerExceptionResolver

스프링 MVC는 컨트롤러 밖으로 예외가 던져진 경우, 예외를 해결하고 동작을 새로 정의할 수 있는 인터페이스를 제공한다.

- 컨트롤러에서 예외가 발생했을 때, `DispatcherServlet`으로 단순히 예외가 전달된 후에 `postHandle`을 skip하고 `afterCompletion`으로 진행하던 것에서,

- `DispatcherServlet`에 `ExceptionResolver`를 호출하여 **예외를 해결하고자 시도**하게 된다.
  
  - 물론 여전히 `postHandle`은 호출되지 않는다. `afterCompletion`만 호출된다.
  
  - `ExceptionResolver`에서 예외가 해결됐다면, `render(model)`을 호출하여 HTML 응답으로 이어진다.

#### 예제 코드 요약

- `HandlerExceptionResolver` 인터페이스를 구현하고 `WebMvcConfigurer` 에서 `extendHandlerExceptionResolvers` 메서드를 통해 등록하면, `HandlerExceptionResolver` 클래스를 커스터마이징하여 적용할 수 있다.
  
  - `configureHandlerExcceptionResolvers(...)` 라는 메서드로 등록하는 것도 가능한데, 이러면 스프링이 기본으로 등록한 `HandlerExceptionResolver`가 **삭제**되는 효과를 내므로 지양한다.

- `HandlerExceptionResolver` 클래스에서 `resolveException` 메서드를 오버라이드하면서, `IllegalArgumentException`을 받아다가 400으로 처리하는 로직을 짤 수 있다.
  
  - 정확히는 발생했던 **예외를 먹어버리면서**,
  
  - `response.sendError()`를 통해 원하는 상태코드로 바꿔치기하는 방식이다.
  
  - 변경된 상태코드를 바꾸는 처리가 적용되기 위해서는, `new ModelAndView` 를 리턴함으로써 **해당 예외를 catch한 효과가 나타나도록**해줘야 한다.
    
    - `response.sendError()` 하더라도 `return null` 로 이어졌다면, 결과적으로 해당 `ExceptionResolver`는 인입된 예외를 그대로 뱉어내는 것에 불과하게 된다.

#### 반환 값에 따른 동작 방식

`ExceptionResolver` 반환 값에 따라 `DispatcherServlet`의 다음 동작을 바꿀 수 있다.

- 빈 `ModelAndView` : 별도의 뷰를 렌더링하지 않고, **정상 흐름**으로 서블릿을 리턴한다.

- `ModelAndView` 지정 : **지정된 뷰를 렌더링**한다.

- `null` : **다음** `ExceptionResolver`를 찾아서 실행한다. 실행 가능한 `ExceptionResolver`가 없다면 그대로 WAS까지 예외가 전파된다.

#### ExceptionResolver 활용

**예외 상태 코드 변환**

- 예외를 `response.sendError()` 호출로 변경하여, **상태 코드에 따른 오류를 처리하도록** 재가공

- 이후 WAS가 서블릿 오류 페이지를 찾아서 내부적으로 호출함

**뷰 템플릿 처리**

- `ModelAndView` 에 값을 채워서 예외에 따른 새로운 오류 화면을 렌더링

**API 응답 처리**

- `response.getWriter().println("hello")` 처럼, HTTP 응답 바디에 데이터를 직접 넣어서 반환하는 것도 가능함.

### 예외를 여기서 마무리하려면?

예외가 WAS까지 던져지고, WAS가 오류페이지를 찾아서 내부 호출하는 과정은 사실 비효율적인 면이 있다.

`ExceptionResolver` 선에서 예외를 끝내버리는 방법은?

- `response.sendError()` 가 아니라, `response.setStatus()` 를 통해 원하는 상태코드를 리턴하면서,

- `Content-Type` 에 맞게 적절한 응답값을 지정해 넣고 `new ModelAndView()`를 리턴하면 된다.

- WAS에서는 **예외 발생 사실** 자체를 모르게 되고, 결과적으로 수많은 내부 호출이 애초에 발생하지 않게 된다.

### 정리

`ExceptionResolver`를 잘 사용하면 예외를 **완전히 처리**해버릴 수 있기 때문에, WAS는 예외 발생 사실을 아예 모르게 될 수가 있다. 

서블릿 컨테이너까지 예외가 올라갈 시 추가 프로세스가 상당히 복잡하고 비효율적이기 때문에, 이렇게 처리하는 쪽이 깔끔하다.

다만, `ExceptionResolver`를 직접 구현하기가 상당히 번거로우므로 스프링이 제공하는 기본 클래스를 확인해본다.

---

## API 예외 처리 - 스프링이 제공하는 ExceptionResolver

스프링부트는 `HandlerExceptionResolverComposite`에 다음 순서로 

`ExceptionResolver`를 기본 등록해준다.

- `ExceptionHandlerExceptionResolver`

- `ResponseStatusExceptionResolver`

- `DefaultHandlerExceptionResolver`

### ResponseStatusExceptionResolver

예외에 따라서 **HTTP 상태 코드를 지정해주는** 역할을 한다.

다음 두 가지 경우를 처리한다.

- `@ResponseStatus`가 달려있는 예외

- `ResponseStatusException`

#### 예제 코드 요약

- Exception 클래스를 하나 만들면서 `@ResponseStatus` 애노테이션을 적용한다.
  
  - 상태코드와 reason을 명시할 수 있다.

- 컨트롤러에서 해당 Exception을 `throw` 하도록 처리하면,

- 호출 결과에서 `@ResponseStatus` 를 통해 지정된 값들이 돌아오는 것을 확인 가능하다.

#### 내부 로직

`ResponseStatusExceptionResolver`는 스프링부트가 기본적으로 등록해둔 **두 번째** `ExceptionResolver`로써, 앞서서 `ExceptionHandlerExceptionResolver`를 구현한 바가 없기 때문에 자연스럽게 호출된다.

내부 로직을 까보면, 앞서 구현했던 `response.sendError()`와 `return new ModelAndView()`를 그대로 확인할 수 있다.

- WAS가 오류 페이지를 내부 요청하는 로직이 발생한다는 뜻

#### 메시지 기능

`@ResponseStatus`에서 `reason` 값을 직접 입력할 수 있는데, 해당 내용을 `MessageSource`로도 처리할 수 있다.

- `messages.properties`에서 메시지 코드값과 그 내용을 등록하고,

- `reason` 필드에서 오류 메시지 코드값을 기재하면,

- 실제 오류 호출 결과에서는 `MessageSource` 처리된 내용이 표시된다.

#### ResponseStatusException

`@ResponseStatus`는 **개발자가 직접 만든 예외에서만 적용이 가능하다**.

- 라이브러리의 예외에 대해서는 적용이 불가능하다.

이런 경우에 대해, `ResponseStatusException`을 통해 **직접 변경할 수 없는 예외를 감싸서** 처리할 수 있다.

- `throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());`

- 위와 같이 입력하여, 개발자가 직접 코드를 수정할 수 없는 `IAE` 에 대해 `@ResponseStatus` 처리한 것과 동일하게 상태코드를 변환할 수 있다.

### DefaultHandlerExceptionResolver

스프링 **내부에서 발생하는 스프링 예외**를 해결한다.

- 대표적으로 파라미터 바인딩 시점에 타입이 맞지 않는 경우.

- `TypeMismatchException`이 발생했을 때 별다른 처리가 없으면 500이 발생하는데, 스프링 구조를 따져봤을 때 해당 예외는 99% **클라이언트의 호출 오류**라고 봐야 한다.

- 이런 케이스에 대해 스프링이 400 오류로 변환하기 위해, `DefaultHandlerExceptionResovler`를 들고 다니면서 사용하고 있다.

내부 로직을 살펴보면, 마찬가지로 `response.sendError()` 가 활용되고 있다.

### 정리

지금까지 정리한 2개의 클래스는 HTTP 상태 코드를 변경하는 쪽에 중점을 두고 있다.

이러한 방식은 직접 사용하기에 다소 복잡한 면이 있고, 무엇보다도 `response`에 데이터를 직접 코딩해서 넣어야하기 때문에 번거로우며, `ModelAndView`를 리턴하는 방식이라서 API에 적절하지 못하다.

스프링은 이 문제를 해결하기 위해, 가장 우선순위가 높은 클래스는 `ExceptionHandlerExceptionResolver`를 제공한다.

---

## API 예외 처리 - @ExceptionHandler

### HTML 화면 오류 vs API 오류

웹 브라우저에서 SSR로 HTML 화면을 제공하는 상황일 때는 단순히 `BasicErrorController` 를 사용하는 것으로 충분하다.

하지만 **API**의 경우, 각 시스템에 따라 응답 인터페이스나 스펙이 모두 다르고, 예외 응답 자체에서 로직 제어가 필요한 경우가 많다.

즉, `BasicErrorController` 하나만 사용하기는 어렵고, `HandlerExceptionResolver` 를 구현하기에는 번거로운 동시에 부적절하다.

#### API 예외처리의 어려운 점

- `HandlerExceptionResolver`가 `ModelAndView`를 반환해야한다는 점

- `response` 객체에 직접 응답 데치터를 코딩해서 넣어야 하는 점

- 특정 컨트롤러에서만 발생하는 예외를 처리하기가 어렵다는 점
  
  - Exception 클래스 자체에 대해서만 분기가 쉽다.

### @ExceptionHandler

스프링은 애노테이션 기반으로 매우 편리한 예외 처리 기능을 제공한다.

이게 `ExceptionHandlerExceptionResolver` 이며, 스프링이 기본으로 바인딩하는 `ExceptionResolver` 중에 우선순위가 가장 높다.

실무에서도 거의 이걸 사용하게 된다.

#### @ExceptionHandler 예외 처리 방법

**컨트롤러** 클래스 내부에 해당 애노테이션을 선언하고, 컨트롤러에서 발생한 예외 중에 **처리하고 싶은 예외**를 지정해주면 된다.

컨트롤러에서 그 예외가 발생하면 `@ExceptionHandler` 메서드가 호출되며, 여기서 다시 예외를 던지지 않는 한 예외 처리가 종료될 수 있다.

#### 예제 코드 요약

- `@ExceptionHandler` 메서드의 응답 자료형은 자유롭다. 객체를 리턴하면 그게 그대로 JSON 형태로 클라이언트에게 돌아간다. `ResponseEntity` 도 사용 가능하다.

- 별도로 `@ResponseStatus` 를 선언하거나 `ResponseEntity` 를 사용하지 않는다면, 클라이언트 응답은 200 코드로 돌아가게 된다. 이는 적절하지 않기 때문에, 정확한 상태코드를 명시해줄 필요가 있다.

#### 우선순위

**항상 자세한 것이 우선권을 가진다**.

`@ExceptionHandler`에서 특정 예외를 지정했으면, 그 자식 클래스까지 처리할 수 있다.

하지만 자식 클래스가 따로 지정되어 있으면, 그 쪽 `@ExceptionHandler`에서 처리하게 되어 있다.

- 비즈니스 로직 상 중요한 예외를 중점적으로 지정하여 처리하고

- `Exception` 자체를 통째로 잡아서 그 외 예외를 처리하면 적절하다.

#### 다양한 예외

`@ExceptionHandler` 에서 `{}` 안에 다양한 예외 클래스를 명시할 수 있다.

#### 예외 생략

예외 클래스를 생략해두면, **메서드 파라미터에서 지정한** 예외에 대해 처리한다.

#### 파라미터와 응답

위에 언급된 바와 같이 **굉장히 다양한 파라미터와 응답**을 지원하고 있다.

거의 컨트롤러 수준의 자유도 하에 사용할 수 있다.

### 실행 흐름 정리

- 컨트롤러 호출 후 예외가 발생할 시, `ExceptionResolver`가 작동한다.

- 그 중 가장 우선순위가 높은 `ExceptionHandlerExceptionResolver` 가 가장 먼저 호출되며, 해당 컨트롤러에서 해당 예외를 처리할 수 있는지 조회한다.

- 적절한 `@ExceptionHandler`가 있다면, 핸들링 메서드가 실행되며 그에 따라 응답값이 세팅된다. 
  
  - 응답은 자동으로 JSON 형변환되며, 상태코드는 지정한 대로 돌아간다.

### 그 외의 경우

#### HTML 오류 화면

`@ExceptionHandler`가 `ModelAndView`를 리턴해주면, 그대로 오류 화면 응답이 발생하게 된다.

- `@RestController`에서 주로 사용해야 하기 때문에 적절한 방법은 아니다.

---

## API 예외 처리 - @ControllerAdvice

`@ExceptionHandler`를 사용해서 예외 처리가 깔끔해질 수는 있는데, **정상 코드와 예외 처리 코드가** 하나의 클래스에 섞이게 된다는 문제가 발생한다.

이 때, `@ControllerAdvice`를 적용하면 예외 처리 코드를 깔끔하게 분리할 수 있다.

### @ControllerAdvice

`@ControllerAdvice`는 대상으로 지정한 **여러** 컨트롤러에 `@ExceptionHandler`, `@InitBinder` 기능을 부여해준다.

- 별도로 대상을 지정하지 않으면 **모든 컨트롤러에** 글로벌로 적용된다.

`@RestControllerAdvice`로 사용하면 `@RestController` 처럼 처리된다.

컨트롤러를 대상으로 하는 **AOP**의 성격이 존재한다.

#### 대상 컨트롤러 지정 방법

공식 문서에서 다양한 방법을 확인 가능하다.

- 특정 **애노테이션**이 있는 컨트롤러를 지정하거나

- 특정 **패키지**를 지정하거나

- 아예 특정 클래스를 지정하는 방법 등


