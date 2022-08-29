# Web Architecture

Web Browser

- Client가 가지고 있는 자원

- data 발생, Server에 요청

Web Server

- 여기부터 Server의 영역

- Client 접속, 응답 처리

Application Server

- Client 요청에 대한 Logic 처리.

- JDBC로 DB와 통신

- Web+Application Server를 WAS라고 함

RDBMS

- Database를 통제하는 부분.

---



# Servlet

자바를 사용해 웹페이지를 동적으로 생성하는 서버 측의 프로그램.

----



## Servlet life-cycle

Servlet class는 javaSE의 class와 달리 main method가 없다. 즉 객체 생성부터 사용의 주체가 사용자가 아닌 Servlet container에게 있다.

Client가 요청을 하게 되면, servlet container는 Servlet 객체를 한번만 생성하여 초기화하고, 요청에 대한 처리를 반복적으로 수행한 뒤, 필요 없게 되면 제거한다.

---



## 주요 method

`init()`: 서블릿이 메모리에 로드될 때 한번 호출. 코드 수정으로 다시 로드 시 다시 호출

`doGet()`, `doPost()`: GET과 POST방식으로 data 전송 시 호출

`destroy()`: 서블릿이 메모리에서 해제되면 한번만 호출

---



## Servlet Parameter

전송방식: GET, POST

### GET

데이터가 URL 뒤 쿼리 스트링으로 전달. 입력 값이 적거나 데이터 노출이 문제 없는 경우 사용.

- 간단한 데이터를 빠르게 전송, 직접 URL을 통제하는 것도 가능

- 데이터 양에 제한이 있음

### POST

URL과 별도로, http의 body에 입력 **스트림 데이터**로 전달됨.

- 데이터의 제한이 없고, 보안 유지 효과

- 전송 패킷을 body에 데이터로 구성해야 해서 GET보다 느리다.

----



# JSP (Java Server Page)

## JSP

HTML 내에 자바 코드를 삽입해, 웹 서버에서 동적으로 웹 페이지를 생성하여 브라우저로 돌려주는 **언어**.

실행 시 Servlet으로 변환된 후 실행되므로 서블릿과 사실상 동일하지만, 디자인이 더 편리하다.

HTML의 껍데기를 가지고 있지만, 확장자가 jsp이고 실행 이후에는 특정 경로에서 Servlet 형식으로 변환되어 있는 것을 직접 확인할 수 있다.

---



## JSP Scriptlet

### 선언 (Declaration)

`<%! 멤버변수와 메서드를 작성 %>`

멤버변수나 메서드를 선언. 주로 head 부분에 작성

### 스크립트릿 (Scriptlet)

`<% java code %>`

Client 요청 시 매번 호출되는 영역. Servlet 변환 시 service() method에 해당되는 영역.

request, response에 관련된 자바 코드를 body의 적재적소에서 구현

### 표현식 (Expression)

`<%= 문자열 %>`

데이터를 브라우저에 출력할 때 사용. 선언해놓은 변수를 사용하는 효과가 있다.

ex) String name이 있다고 할 때,

`<%= name %>`으로 html 중간에 삽입할 수 있고,

`<% out.print(name); %>`와 동일한 기능을 구현한다.

### 주석 (Comment)

`<%-- 주석 할 code --%>`

HTML 주석과 달리 전송 자체가 안 된다. 콘솔에도 안 찍힌다.

만약 HTML 주석 속에 JSP 주석을 넣어놓으면, 일단 서버에 들어가긴 하므로 **실행이 되어버린다**.

- HTML은 Java 코드를 실행시키지 않을 권한이 없다.

### 주의사항

**자바는 스크립트 언어들보다 먼저 실행된다.**

- JS 같은 스크립트 언어 속 조건문 (if, else)으로 자바의 실행 여부를 제어하는 게 불가능하다.

- 스크립트에 담겨 있는 자바 코드는 서버로 전송된 뒤, **무조건** 실행된다. 그게 에러가 뜨면, 스크립트 문법과 무관하게 전체 기능이 터진다.

----



## JSP Directive

### page 지시문

`<%@ page attr="val1" %>`

컨테이너에게 현재 JSP 페이지를 어떻게 처리할 것인지 정보를 제공.

jsp 파일 처음 생성 시 자동으로 찍혀있는 것을 확인 가능하다.

12개가 나열되어 있는데, 대부분은 만질 일이 없다. 중요한 건 **session** 속성의 기본값이 true라는 점

### include 지시문

`<%@ include file="/test.jsp" %>`

특정 jsp file을 페이지에 포함시키는 지시문

여러 개의 jsp 파일에서 반복적으로 사용되는 코드가 있다면, 그걸 따로 jsp 파일로 만든 뒤 include시켜서 반복되는 코드를 줄일 수 있다.

### taglib 지시문

JSTL 또는 사용자에 의해 만든 커스텀 태그를 이용할 때 사용된다.

----



## JSP 기본객체

### 기본 객체

#### request

HTML 폼 요소의 선택 값 등 사용자의 input을 읽어올 때 사용

#### response

사용자 요청에 대한 응답 처리를 위해 사용

#### pageContext

각종 기본객체를 얻거나, **forward** 및 include 기능 활용 시 사용

#### session

클라이언트에 대한 세션 정보 처리를 위해 사용.

page directive의 session 속성을 조절 가능하다.

#### out

사용자에게 전달하기 위한 output 스트림 처리 시 사용

#### 그 외

application, config, page, exception

### 기본 객체의 영역

#### pageContext

하나의 JSP 페이지를 처리할 때 사용되는 영역.

한번에 요청에 대해 하나의 jsp 페이지 호출. 단 한 개의 page 객체에만 대응

페이지 영역에 저장한 값은 페이지를 벗어날 시 **사라진다.**

#### request

하나의 http 요청을 처리할 때 사용되는 영역.

브라우저 요청 시 새로운 request 객체 생성.

영역 내 저장한 속성은 그 요청에 대해 응답(response) 완료 시 사라진다.

#### session

하나의 웹 브라우저와 관련된 영역.

같은 브라우저 내 요청되는 페이지들은 같은 session을 공유. 로그인 정보 등을 저장

#### application

하나의 웹 어플리케이션과 관련된 영역. 어플리케이션 하나 당 하나의 객체만 생성되고, 안의 페이지들은 같은 객체를 공유함.

### 공통 메서드

servlet과 jsp 간에 정보를 주고 받기 위해 메서드가 지원된다. Attribute의 형태로 주고받게 됨.

### WEB Page 이동

#### forward

`RequestDispatcher` 객체를 생성해 `forward` 메서드에 request와 response를 넣어준다.

기존 URL을 유지하고, 기존 request와 response도 그대로 전달된다.

#### redirect

`response` 객체의 `sendRedirect` 메서드에 location (full URL)을 넣어준다.

타 URL로 이동이 가능하고, 기존 request와 response는 소멸한다. 데이터 유지하려면 session이나 cookie를 이용해야 한다.
