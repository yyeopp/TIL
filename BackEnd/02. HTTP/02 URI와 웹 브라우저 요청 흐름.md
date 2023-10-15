# URI와 웹 브라우저 요청 흐름

---

## URI

URI는 로케이터(locator), 이름(name) 또는 둘다 추가로 분류될 수 있다.

URI는 기본적으로 **Resource Identifier**

- 하위 개념에 해당하는 것이 URL과 URN

- Resource Locator

- Resource Name

### URI: 단어의 뜻

- Uniform: 리소스를 식별하는 **통일된** 방식

- Resource: 자원. URI로 식별할 수 있는 **모든 것**

- Identifier: 다른 항목과 구분하는 데 필요한 정보

### URL, URN

- URL: **Locator** 리소스가 있는 **위치**를 지정

- URN: **Name** 리소스에 **이름**을 부여.

위치는 변할 수 있지만, 이름은 변하지 않는다.

- 이걸 활용한 사례가 책의 isbn 정도.

근데, URN 이름만으로 리소스를 찾는 방법이 **보편적이지는 못하다.**

사실상 **동작 가능한 URI 방식**은 **URL 뿐**인 상황인 것.

그래서, URI를 URL과 **같은 의미처럼 이야기해도 무방**하다.

### URL 전체 문법

`scheme://[userinfo@]host[:port][/path][?query][#fragment\]`

#### scheme

주로 프로토콜을 적는다.

- **프로토콜**: 어떤 방식으로 자원에 접근할 것인가
  
  - http, https, ftp 등등

#### userinfo

URL에 사용자정보를 포함하여 인증해야할 때 사용

거의 쓰지 않음

#### host

호스트명. 도메인 혹은 IP 주소

#### PORT

접속 포트를 입력

일반적으로 생략

- http면 80, https면 443이 기본

#### path

리소스의 경로.

**계층 구조**를 적시하게 된다.

#### query

key=value 의 형태.

`?`로 시작하고, `&`으로 추가해나갈 수 있다.

query parameter 나 query string 으로 부름

#### fragment

html 내부에서 북마크 등에 사용한다.

서버로 전송되는 정보는 아님

---

## 웹 브라우저 요청 흐름

`https://www.google.com:443/search?q=hello&hl=ko`

1. 클라이언트 웹 브라우저의 DNS 조회
   
   - IP, PORT 정보 조회 완료

2. HTTP 요청 메시지 생성
   
   - 요청 메시지 상세 내용은 나중에

3. HTTP 메시지 전송 시작
   
   1. 브라우저가 HTTP 메시지 생성
   
   2. SOCKET 라이브러리 통해 OS로 전달
   
   3. TCP/IP 패킷이 생성됨 (**HTTP 메시지** 포함)
   
   4. 네트워크로 출발

4. 구글 서버에 도착
   
   - 패킷을 까서, HTTP 메시지를 서버에 보냄
   
   - HTTP 응답 메시지를 생성

5. 응답 HTTP 메시지 생성 및 전송

6. 클라이언트 웹 브라우저에서 HTML을 받고 렌더링


