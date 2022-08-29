# MVC Pattern

## Web Application Architecture

JSP를 이용하여 구성할 수 있는 WAA는 크게 **model1**과 **model2**로 나뉜다.

JSP가 client의 요청에 대한 logic 처리 및 response page(view)에 대한 처리를 모두 하는지,

아니면 response page(view)에 대한 처리**만** 하는지가 가장 큰 차이점.

## Model1

model1은 view와 logic을 JSP 페이지 하나에서 처리하는 구조.

client로부터 요청이 들어오면, JSP 페이지는 java beans나 별도의 service class를 이용하여 작업을 처리, 결과를 출력한다.

- WAS는 JSP와 model로 구성

- JSP는 view와 controller로 구성

- model은 RDBMS와 소통

### 장점

구조가 단순하며, 직관적이다. 

개발 비용이 감소한다.

### 단점

출력을 위한 view(html) 코드와 로직 처리를 위한 java 코드가 섞여 있어 JSP 코드가 복잡해진다.

JSP 코드에 백과 프론트가 혼재되어 있어 분업이 어렵다.

프로젝트 규모가 커질수록 코드가 복잡해져, 유지보수가 어렵다.

확장성(신기술 도입, 프레임워크 사용)이 나쁘다.

## Model2 - MVC Pattern

### 개요

모든 처리를 JSP 페이지에서 하는 것이 아니라, client 요청에 대한 처리는 **servlet**이, logic 처리는 **java class인 Service, DAO 등**이, client에게 출력하는 response page는 **JSP**가 담당.

MVC pattern을 웹개발에 도입한 구조.

- Model: Logic을 처리하는 모든 것. controller로부터 넘어온 data를 이용해 logic을 수행하고 결과를 다시 controller에 return한다.

- view: 모든 화면 처리를 담당. client 요청에 대한 결과와 controller에 요청을 보내는 화면단을 jsp로 처리.
  
  - logic 처리를 위한 java code는 사라지고, 결과 출력을 위한 code만 남았다.

- controller: client 요청을 분석해, logic 처리를 위한 model을 호출. return 받은 결과를 필요에 따라 request나 session에 저장하고, redirect 또는 forward 방식으로 jsp를 이용해 출력.

### 장점

출력을 위한 view 코드와 로직 처리를 위한 java 코드가 분리되어, 코드가 복잡하지 않다.

화면단과 logic단이 분리되어 분업이 용이하다.

기능에 따라 code가 분리되어 유지보수가 쉽다.

확장성이 뛰어나다.

### 단점

구조가 복잡해 초기 진입이 어렵다

개발 비용이 증가한다.
