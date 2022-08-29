# SpringFramwork - MVC

----

# Spring Web MVC

## MVC Pattern

### Model

어플리케이션 상태의 캡슐화

상태 쿼리에 대한 응답

어플리케이션의 기능 표현

변경을 view에 통지

### View

모델을 화면에 시각적으로  표현

모델에게 업데이트 요청

사용자 입력을 컨트롤러에 전달

컨트롤러가 view를 선택하도록 허용

### Controller

어플리케이션의 행위 정의

사용자 액션을 모델 업데이트와 mapping

응답에 대한 view 선택

### 정리

어플리케이션 확장을 위해 세 가지 영역을 분리한 것.

컴포넌트의 변경이 다른 영역 컴포넌트에 영향을 미치지 않음. **유지보수**에 용이

컴포넌트 간의 결합성이 낮아 프로그램 수정이 용이. **확장성**이 뛰어남

### 장점

### 단점

## MVC 요청의 흐름

1. Client가 Controller에게 데이터와 함께 request를 보냄

2. Servlet (Controller)가 Model 속 Service object에게 call

3. Service object가 DAO에게 call

4. DAO가 SQL 언어로 DB에 요청

5. 반대로 result 전송

## Spring MVC

### 특징

Spring은 DI나 AOP 같은 기능 뿐만 아니라, **Servlet** 기반의 WEB 개발을 위한 MVC Framework를 제공함

- 근데, 기존에 있던 Servlet 관련 코드는 보이지 않음

Model2 Architecture와 Front Controller Pattern을 framework 차원에서 제공함

- 개발자는 실제 비즈니스 로직만 신경쓰고(login 메서드 등), act 선별이나 메서드 호출, redirect/forward 같은 **Front Controller** 처리는 Framework가 자동으로 해준다.

### 구성요소

#### `DispatcherServlet`

Front Controller의 역할. 모든 클라이언트의 요청을 전달받고, Controller에 전달하며, Controller가 리턴한 결과값을 View에게 전달함.

#### `HandlerMapping`

클라이언트의 요청 URL을 어떤 Controller가 처리할지를 결정

URL과 요청 정보를 기준으로 어떤 핸들러 객체를 사용할 지 결정하는 객체.

- DispatcherServlet은 하나 이상의 핸들러 매핑을 가지게 될 것

#### `Controller`

클라이언트의 요청을 처리한 뒤, Model을 호출하고 그 결과를 DispatcherServlet에 알려준다.

개발자가 직접 처리해야하는 부분.

#### `ModelAndView`

Controller가 처리한 데이터 및 화면에 대한 정보를 보유한 객체

#### `ViewResolver`

Controller가 리턴한 뷰 이름을 기반으로 Controller의 처리 결과를 보여줄 View를 결정

#### `View`

Controller의 처리결과를 보여줄 응답화면을 생성

### Spring MVC 요청 흐름

1. Client가 DispatcherServlet에 요청 (예시: URI 상 /login)

2. DispatcherServlet이 HandlerMapping에 **어떤 컨트롤러의 어떤 메서드를 사용해야 하는지 요청.**
   
   굳이 따지면 예전 if~else 문

3. 처리 컨트롤러가 DispatcherServlet으로 return.

4. DispatcherServlet이 Controller에 처리 요청. Controller 속 메서드는 개발자의 몫
   
   - data get, service call, response page 통보가 이루어질 것. (Service 쪽은 알아서 구성)

5. DispatcherServlet으로 **ModelAndView**를 리턴. 단 이 때 View는 **이름**만 전달

6. DispatcherServlet이 ViewResolver에 View 객체를 요청. 구체적으로 어디를 가야할 지

7. ViewResolver가 실제 페이지를 구성해서 View를 리턴

8. DispatcherServlet이 View에 응답생성을 요청 (기본값은 forward)

9. View가 Client에게 JSP를 생성

개발자가 신경 써야 할 부분은 Controller에 불과함. 나머지는 거의 전부 자동으로 이루어짐

대신, XML과 Annotation을 이용한 **Setting** 작업이 매우 중요할 것

### Spring MVC 구현

1. web.xml에 DispatcherServlet 등록 및 Spring 설정파일 등록

2. 설정 파일에 HandlerMapping 등록

3. Controller 구현 및 Context 설정 파일에 등록

4. Controller와 JSP 연결을 위해 View Resolver 설정

5. JSP 코드 작성

좋은 디자인은, Controller가 많은 일을 하지 않고 Service에 처리를 위임하는 것.

### 실행 순서

1. 웹 어플리케이션 실행 시 WAS(Tomcat)에 의해 web.xml이 로딩

2. web.xml에 등록되어 있는 `ContextLoaderListener`가 생성.
   
   - `<listener>`의 형태로 등록되어 있음.
   
   - ApplicationContext를 생성하는 역할을 수행

3. 생성된 `ContextLoaderListener`가 root-context.xml을 로딩

4. root-context.xml에 등록되어 있는 Spring Container가 구동.
   
   - 주의할 점: component-scan을 여기서 돌리면, `DispatcherServlet`이 생성되기 전에 Controller bean이 생성된다.
     
     이렇게 만들어진 Controller bean은 DispatcherServlet과 연결을 형성하지 **못한다!**
   
   - 즉, component-scan은 나중에 하는 게 맞다.

5. Client로부터 요청이 들어옴 (request)
   
   - 이 때 요청은 pageLoading 시의 request를 포함하고 있다.
   
   - 4번과 5번 사이의 텀은 거의 없다시피 하지만, **순서**는 존재한다는 게 중요

6. web.xml로부터 `DispatcherServlet`이 생성됨.
   
   - `DispatcherServlet`은 FrontController의 역할을 수행함.
   
   - Client로부터 요청된 메시지를 분석해서, 알맞은 PageController에게 전달하는 것이 목적.
   
   - 실질적인 작업은 PageController에서 이루어짐

7. `DispatcherServlet`은  servlet-context.xml을 로딩함.
   
   - `<component-scan>`은 여기서 돌리는 게 적절하다.

8. servlet-context.xml에 등록된 Spring Container (두번째)가 구동되며, 응답에 맞는 PageController를 동작시킴.
   
   - 생성된 객체들이 협업하여, 작업을 처리한다.
