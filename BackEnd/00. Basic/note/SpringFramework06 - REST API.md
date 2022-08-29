# SpringFramework - REST API

---

# REST API

## OPEN API?

**Application Programming Interface**

OPEN API는 프로그래밍에서 사용할 수 있는, 개방되어 있는 상태의 Interface

- 어떤 형식으로 데이터를 주겠다는, **약속**

공공데이터 포털을 대표적으로, 포털 사이트가 가지고 있는 데이터를 외부 응용 프로그램에서 사용할 수 있도록 OPEN API가 제공됨

그리고 대부분의 OPEN API가 REST 방식으로 지원된다.

## REST

**Representational State Transfer**

하나의 URI는 하나의 고유한 리소스(Resource)를 대표하도록 설계된다는 개념에, 전송방식을 결합해서 원하는 작업을 지정함

웹의 장점을 최대한 활용할 수 있는 아키텍처로써 REST를 발표

HTTP URI를 통해 제어할 자원(Resource)을 명시하고, HTTP Method(GET, POST, PUT, DELETE)을 통해 해당 자원을 제어하는 명령을 내리는 방식의 아키텍처.

## REST 구성

- 자원(Resource) - URI

- 행위(Verb) - HTTP Method

- 표현(Representations)

잘 표현된 HTTP URI로 리소스를 정의하고

HTTP method로 리소스에 대한 행위를 정의한다.

리소스는 JSON, XML과 같은 여러 가지 언어로 표현할 수 있다.

> /blog/view?no=25&id=ssafy 대신
> 
> /blog/ssafy/25라고 URI를 보낼 때 **GET**을 명시

## 기존 Service와 REST Service

### 기존

요청에 대한 처리를 한 후 가공된 data를 이용해 **특정 플랫폼에 적합한** 형태의 **view로 만들어서** 반환함.

- 간단히 말해, 백엔드에서 html을 만들어서 전달해야 함

- Web 사용자와 Mobile 사용자를 구분해서 각기 다른 html을 전달해야할 수도 있음. (최적화된 View 제공을 위해)

- Client 요청 방식에 제한은 없음.

### REST

data 처리만 한다거나, 처리 후 반환될 data가 있다면 **JSON이나 XML** 형식으로 전달한다. **View에 대해 신경 쓸 필요가 없다.**

- 프론트엔드에서 jQuery AJAX로 OPEN API를 받아오던 서비스가 정확히 이 방식

- 대신 요청 방식에는 제한이 생긴다. REST 아키텍처 방식

## REST

기존 전송방식과 달리, 서버는 요청으로 받은 리소스에 대해 순수한 데이터를 전송한다.

기존 GET/POST 외에 PUT, DELETE 방식을 사용하여 리소스에 대한 **CRUD** 처리를 할 수 있다.

- 쓰기 POST, 읽기 GET, 수정 PUT, 삭제 DELETE

- 기존에는 GET과 POST만으로, URI를 바꿔가면서 처리했던 것에 비해 간명해짐

자원을 표현할 때 Collection(문서, 객체의 집합)과 Document(하나의 문서, 객체) 사용.

> http://www.ssafy.com/sports/baseball/players/31
> 
> **복수형**을 어디서 쓰는지 잘 확인하기

가장 큰 단점은, 정해진 표준이 없이 암묵적 표준만 정해져 있다는 점. 아래 규칙을 벗어나도 에러가 나는 것은 아님

- 하이픈(-) 사용 가능, 언더바(_) 사용 불가 (가독성)

- 특별한 경우를 제외하고 대문자 사용은 하지 않음

- URI 마지막에 슬래시(/)를 사용하지 않음

- 슬래시로 계층 관계로 나타냄

- 확장자가 포함된 파일 이름을 직접 포함시키지 않음

- URI는 명사를 사용

## REST 관련 Annotation

### `@RestController`

Controller가 REST 방식을 처리하기 위한 것임을 명시

### `@ResponseBody`, `@RequestBody`

JSP와 같은 뷰로 전달되는 것이 아니라 **데이터 자체를 전달하는 것**이라고 표시해주고,

- API를 다루는 Controller를 따로 만들에서 `@RestController`를 달아주면 메서드마다 달아둘 필요가 없다.

JSON 데이터를 원하는 타입으로 바인딩해줌

### `@PathVariable`

URL 경로에 있는 값을, 파라미터로 추출함

### `@CrossOrigin`

Ajax의 크로스 도메인 문제를 해결

- http와 https 간 통신문제가 대표적

## REST API 설정

### Jackson library

`jackson-databind` 라이브러리는 객체를 JSON 포맷의 문자열로 자동 변환시켜서 브라우저로 전송한다.

- 원래는 JSON 객체나 JSON Array 객체를 직접 만들어서 하나씩 입력하는 게 맞는데,

- DTO를 그대로 return시켜도 알아서 바꿔주게 된다.

`jackson-dataformat-xml` 라이브러리는 xml로 전송한다.

## 실습하면서 알게된 Tip

- `RestController`의 return 타입은 주로 `ResponseEntity<?>`가 사용된다.
  
  - 서비스 수행 후 객체와 `HttpStatus`를 함께 return해줄 수 있는 장점이 있다.
  
  - 객체의 타입도 서비스에 맞게 자유롭게 지정 가능하다.

- Controller에서 메서드 파라미터에 `@RequestParam` 혹은 `@RequestBody`, `@PathVariable`을 정확히 선언해줄 필요가 있다.
  
  - `@RequestBody`를 선언해주지 않으면 Postman에서 body로 값을 넣어줘도 읽어오지 못한다.
  
  - `@PathVariable`은 URL상 path로 값을 넘겨줄 때 사용된다.
    
    - Get으로 호출할 때 쿼리문을 사용하지 않기 위한 대용품 느낌으로 보면 된다.
    
    - Mapping 시 `/user/{id}` 이런 식으로 선언한다.

- `@CrossOrigin` 선언은 `origins`와 `methods`에 대해 가능하다.
  
  - 보통 `origins` 는 `*`로 전부 풀어줄 수 있고
  
  - `methods`는 `RequestMethod.GET` 이런 식으로 나열 가능하다.

- Mapping 시 

---

# Swagger

## Swagger를 이용한 REST API의 문서화

프로젝트 개발 시 일반적으로 프론트와 백엔드 개발자가 분리

프론트 개발자의 경우, 화면과 로직에 집중을 하고 백엔드 개발자가 만든 문서 API를 보며 데이터 처리를 하게 된다.

- 이 때 개발 상황의 변화에 따른 API의 추가 또는 변경할 때마다 문서에 적용하는 불편함 발생

- 이 문제를 해결하기 위해 Swagger를 사용

## 개요

Swagger는, 간단한 설정으로 프로젝트의 API 목록을 웹에서 확인 및 테스트할 수 있게 해주는 library.

Swagger 사용 시 Controller에 정의되어 있는 모든 URL을 바로 확인할 수 있다.

API의 명세 또는 설명을 볼 수 있고. 직접 API를 테스트해볼 수도 있다.
