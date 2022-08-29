# EL & JSTL

## EL (Expression Language)

### 개요

표현을 위한 언어. JSP 스크립트의 표현식을 대신하여, 속성값을 쉽게 출력하도록 고안된 language.

> JSP의 표현식은 `<%= 내용 %>`.

EL 표현식에서 도트 연산자 왼쪽은, 반드시 `java.util.Map` 객체 또는 Java Bean 객체여야 한다.

EL 표현식에서 도트 연산자 오른쪽은 반드시 맵의 key이거나 Bean 프로퍼티여야 한다.

### 기능

JSP의 네 가지 기본 객체가 제공하는 영역의 속성 사용

자바 클래스 메서드 호출 기능

표현 언어만의 기본 객체 제공

수치, 관계, 논리 연산 제공

### 문법

`<%= ((com.ssafy.model.MemberDto ) request.getAttribute("userinfo").getZipDto().getAddress () %>`를,

`${userinfo.zipDto.address}`로 바꿀 수 있다.

`${Map.key}` 혹은 `${JavaBean.property}`의 형식

### 연산자

dot 표기법 외에, `[]`연산자를 사용해 객체의 값에 접근할 수 있다.

.name이랑 ["name"]이랑 같은 결과를 가져온다는 뜻

[]안에 배열이나 리스트의 인덱스를 넣는 것도 가능.

그 외에는 대부분 java와 동일하나,

**empty**가 중요

- 만약 값이 null이거나 빈 문자열(""), 길이가 0인 배열([]), 빈 Map 객체, 빈 Collection 객체면 **true**를 return한다.

### 내장객체

JSP 페이지의 EL 표현식에서 사용할 수 있는 기본제공 객체.

pageContext를 제외한 모든 내장객체는 Map이고, key와 value의 쌍으로 값을 저장한다.

기본문법은 `${expr}`

pageScope, **requestScope**, **sessionScope**, applicationScope를 제공

그 외에도 pageContext, **param**, **cookie** 등

만약 Java Bean의 property 이름만 expr할 경우, **자동으로 pageScope -> requestScope -> sessionScope -> applicationScope 순으로 객체를 찾아준다.**

### 메서드 호출

Java Bean이 가진 property를 호출할 때, 해당 property에 대한 **getter, setter**가 기준이 된다.

## JSTL (JSP Standard Tag Library)

### 개요

custom tag란, 개발자가 직접 jsp의 태그를 작성할 수 있는 기능.

이러한 custom tag 중에서 많이 사용되는 것들을 모아서 **JSTL**이라는 규약을 만들었다.

논리적인 판단, 반복문 처리, 데이터베이스 처리 등을 할 수 있다.

JSP 페이지에서 스크립트릿을 사용하지 않고 액션을 통해 간단하게 처리할 수 있는 방법을 제공

`<%@ taglib prefix="prefix" uri="uri" %>`로 directive 선언.

대부분 **core** library를 사용하게 될 것

### core tag

set: jsp에서 사용할 변수 설정

`<c:set value ="value" var="varName">`

if: 조건에 따른 코드 실행

`<c:if test=${userType eq 'admin}>`

choose-when-otherwise: 다중 조건 처리. if-elseif-else와 유사

catch: Exception 처리

- 기본적으로 JSP는 지정된 오류페이지를 통해 처리하는데, `<c:catch>`액션은 JSP에서 예외가 발생할 만한 코드를 페이지로 넘기지 않고 직접 처리할 때 사용

- `<c:if>` 액션과 함께 사용하면, `try~catch`와 같은 기능 구현 가능

forEach: array나 collection의 각 항목 처리. for와 유사

`<c:forEach var ="course" items="${courses}">`
