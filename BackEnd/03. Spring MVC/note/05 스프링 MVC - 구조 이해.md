# 스프링 MVC - 구조 이해

----

## 스프링 MVC 전체 구조

지금까지 직접 만든 프레임워크는 스프링 MVC와 사실상 구조가 동일하다.

클래스명만 의도적으로 다르게 지은 수준.

### DispatcherServlet

스프링 MVC의 **프론트 컨트롤러**에 해당한다.

스프링 MVC의 핵심이라고 할 수 있음.

#### DispatcherServlet 서블릿 등록

`DispatcherServlet`도 잘 뒤져보면 `HttpServlet` 을 상속받고 있다.

스프링부트의 경우, `DispatcherServlet`을 서블릿으로 **자동 등록**하면서 **모든 경로**에 대해 매핑 처리한다.

- `urlPatterns = "/"`

### 요청 흐름

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse
response) throws Exception {                
    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    ModelAndView mv = null;
// 1. 핸들러 조회
    mappedHandler = getHandler(processedRequest);
    if (mappedHandler == null) {
        noHandlerFound(processedRequest, response);
        return;
    }
// 2. 핸들러 어댑터 조회 - 핸들러를 처리할 수 있는 어댑터
    HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
// 3. 핸들러 어댑터 실행 -> 4. 핸들러 어댑터를 통해 핸들러 실행 -> 5. ModelAndView 반환
    mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
    processDispatchResult(processedRequest, response, mappedHandler, mv,
dispatchException);
}

private void processDispatchResult(HttpServletRequest request,
HttpServletResponse response, HandlerExecutionChain mappedHandler, ModelAndView
mv, Exception exception) throws Exception {
// 뷰 렌더링 호출
    render(mv, request, response);
}

protected void render(ModelAndView mv, HttpServletRequest request,
HttpServletResponse response) throws Exception {
    View view;
    String viewName = mv.getViewName();
// 6. 뷰 리졸버를 통해서 뷰 찾기, 7. View 반환
    view = resolveViewName(viewName, mv.getModelInternal(), locale, request);
// 8. 뷰 렌더링
    view.render(mv.getModelInternal(), request, response);
}
```

실제 스프링MVC의 `DispatcherServlet` 에서 핵심 코드를 발췌하면 위와 같다.

#### 동작 순서

- 핸들러 조회
  
  - 요청 URL, HTTP 헤더에 매핑되는 핸들러를 조회

- 핸들러 어댑터 조회
  
  - 핸들러를 실행할 수 있는 핸들러 어댑터 조회

- 핸들러 어댑터 실행
  
  - 핸들러 어댑터 실행

- 핸들러 실행
  
  - 핸들러 어댑터가 실제 핸들러를 실행

- `ModelAndView` 반환
  
  - 핸들러가 반환하는 정보를, 어댑터가 `ModelAndView`로 **변환**하여 반환

- `viewResolver` 호출
  
  - 뷰 리졸버를 찾아서 실행

- `View` 반환
  
  - 뷰 리졸버가 뷰의 논리 이름을 물리 이름으로 바꿔서, **렌더링**을 담당하는 뷰 객체로 반환

- 뷰 렌더링 
  
  - 뷰를 통해서 실제 화면을 렌더링

#### 주요 인터페이스 목록

- 핸들러 매핑 : `HandlerMapping`

- 핸들러 어댑터 : `HandlerAdapter`

- 뷰 리졸버 : `ViewResolver`

- 뷰 : `View`

#### 정리

스프링 MVC의 내부 구조를 다 파악하는 것은 사실상 불가능하지만, 이걸 내가 직접 확장하는 일은 존재하지 않는다.

- 스프링의 긴 역사동안 뛰어난 개발자들이 모든 케이스를 커버해뒀다.

중요한 건, 핵심 동작 방식을 이해해둔 상태에서 **문제가 발생했을 때** 빠르게 해결할 수 있는 지식을 갖추는 것.

---

## 핸들러 매핑과 핸들러 어댑터

### Controller 인터페이스

지금의 애노테이션 기반 `@Controller` 가 아닌, 과거에 사용하던 `Controller` 인터페이스가 있다.

해당 인터페이스를 구현해서 `OldController`를 제작하고, `@Component`로 등록할 때 `url-pattern`을 입력한다.

해당 URL로 접근하면, `OldController`가 실제 호출되는 것을 확인 가능하다.

### 컨트롤러 호출 과정

결국 2가지가 필요하다. 앞선 예제 코드 작동에 문제가 없었다는 건, 이미 그것들이 갖춰져 있다는 뜻이다.

#### 핸들러 매핑

핸들러 매핑이 `OldController`를 찾은 것이다.

**스프링 빈의 이름으로 컨트롤러를 찾을 수 있는 핸들러 매핑**이 필요하다.

#### 핸들러 어댑터

찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.

#### 스프링부트가 자동 등록하는 핸들러 매핑, 핸들러 어댑터

아래 내용 말고도 더 있지만, 중요한 것만 적으면?

##### HandlerMapping

- `RequestMappingHandlerMapping`
  
  - `@RequestMapping` 에서 사용함

- `BeanNameUrlHandlerMapping`
  
  - 스프링 **빈의 이름**으로 핸들러를 찾음

##### HandlerAdapter

- `RequestMappingHandlerAdapter`
  
  - 마찬가지로 애노테이션 기반에서 사용

- `HttpRequestHandlerAdapter`

- `SimpleControllerHandlerAdapter`
  
  - `Controller` 인터페이스를 위해 사용

#### 호출 과정 결론

- `BeanNameUrlHandlerMapping` 이, 빈 이름으로 등록된 URL을 기반으로 `OldController`를 찾아냈다.

- `SimpleControllerHandlerAdapter`가 `Controller` 인터페이스를 지원하고 있다. `OldController`는 이를 통해 실행 가능하다.

### HttpRequestHandler 사용해보기

구 서블릿과 가장 유사한 형태의 핸들러.

- `HttpRequestHandler`를 구현해서 핸들러를 자체 생성하면,

- 서블릿 객체를 직접 사용하는 핸들러가 만들어진다.

실행해보면 이상이 없다. 앞서 `OldController`와 마찬가지로 그게 왜 가능했는지를 추적해보면,

- `BeanNameUrlHandlerMapping`으로 핸들러 탐색 성공

- `HttpRequestHandlerAdapter`를 통해서 핸들러 실행 성공

### @RequestMapping

핸들러 매핑, 핸들러 어댑터 조회 과정은 **순차 리스트 탐색**이다.

즉, 리스트 내부적으로 **우선순위**가 존재하며,

가장 우선순위가 높은 건 현대 스프링에서 99% 이상 사용하는 **애노테이션 기반 컨트롤러** 관련이다.

- `RequestMappingHandlerMapping`

- `RequestMappingHandlerAdapter`

---

## 뷰 리졸버

### InternalResourceViewResolver

스프링부트는 `InternalResourceViewResolver`라는 뷰 리졸버는 자동으로 등록한다.

해당 클래스는 `application.properties` 에서 postfix와 suffix를 가져다가, 경로 설정 시에 사용한다.

#### 스프링부트가 자동 등록하는 뷰 리졸버

핸들러 매핑, 핸들러 어댑터와 마찬가지로 **어댑터 패턴**이 적용되어 있다.

여러 개가 자동으로 등록되고, 요청마다 처리 가능한 뷰 리졸버를 순차 탐색으로 골라서 가져온다.

- `BeanNameViewResolver`
  
  - 빈 이름으로 뷰를 찾아서 반환함
  
  - 엑셀 파일 생성 기능 등

- `InternalResourceViewResolver`
  
  - JSP를 처리할 수 있는 뷰를 반환

### 뷰 리졸버 동작 방식

- 핸들러 어댑터가 **논리 뷰** 이름을 획득한다.

- 해당 이름으로 `viewResolver`를 호출한다.

- 뷰 리졸버가 `InternalResourceView` 를 반환한다.

- `view.render()` 함수가 호출되면, `InternalResourceView`가 JSP 파일에 해당하는 **포워딩**을 처리한다.

#### 참고

JSTL 라이브러리를 함께 사용한다면, `InternalResourceView`를 상속받은 `JstlView`를 반환하게 된다.

---

## 스프링 MVC - 시작

스프링이 제공하는 컨트롤러는 **애노테이션** 기반으로 동작한다.

처음부터 애노테이션을 사용했던 건 아닌데, `@RequestMapping` 이라는 애노테이션을 붙여줌으로써 컨트롤러가 매우 유연하고 실용적으로 변화했다.

- 과거에는 스프링 프레임워크의 MVC 부분이 약해서 다른 프레임워크를 섞어서 쓰기도 했는데,

- 지금은 스프링이 통일했다.

### @RequestMapping

핸들러 매핑과 핸들러 매핑에서도 가장 우선순위가 높은 것들은 **애노테이션 기반**으로 동작하는 클래스들이다.

- `RequestMappingHandlerMapping` 과 `RequestMappingHandlerAdapter`

실무적으로 애노테이션 기반을 99% 사용 중이므로, 이 방식을 배워야 한다.

### 예제 코드 요약

- 컨트롤러 클래스는 `@Controller` 를 처리하여 스프링 빈으로 등록한다.
  
  - 해당 애노테이션을 사용해야, 스프링 MVC에서 **애노테이션 기반 컨트롤러**로 인식해준다.
  
  - 클래스 레벨에 `@RequestMapping`을 달아주는 것으로도 스프링 MVC에 애노테이션 기반 컨트롤러로 등록할 수 있다.

- `@RequestMapping` 과 url-pattern 을 메서드에 달아서, 요청 정보를 매핑한다.

- 메서드는 `ModelAndView`를 생성해서 리턴한다.
  
  - 메서드에서 `HttpServletRequest, Response` 를 파라미터로 사용할 수 있다.

---

## 스프링 MVC - 컨트롤러 통합

### 예제 코드 요약

- `@RequestMapping`을 사용하면 메서드 단위로 URL을 매핑할 수 있기 때문에, 굳이 **URL 단위로 클래스를 분리**하는 **서블릿 방식**을 따라할 필요가 없게 된다.

- 클래스 수준에서 `@RequestMapping`을 달아서 공통 URL (prefix) 을 지정하고,

- 메서드 레벨에서 논리 URL을 지정해줄 수도 있다.

---

## 스프링 MVC - 실용적인 방식

**실무에서 사용하는 최종 방식**

### 예제 코드 요약

- 굳이 `ModelAndView`를 직접 생성해서 반환하지 않아도 된다. String 형으로 반환하면, view의 논리명으로 인지한다.

- `HttpServletRequest` 를 파라미터에 넣어서 사용할 필요가 없다. `@RequestParam` 이라는 기능을 지원한다.

- `Model` 을 파라미터로 받아서 리턴용으로 사용할 수 있다.

- `@RequestMapping`을 세분화하여, POST, GET, PUT, DELETE 요청을 구분할 수록 만들 수 있다.
  
  - 동일 URL이라도 HTTP 메서드로 추가 분기가 가능
  
  - `@PostMapping`, `@GetMapping` 등


