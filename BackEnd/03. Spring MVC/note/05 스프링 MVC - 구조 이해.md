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


