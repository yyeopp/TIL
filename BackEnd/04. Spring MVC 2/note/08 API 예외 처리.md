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
