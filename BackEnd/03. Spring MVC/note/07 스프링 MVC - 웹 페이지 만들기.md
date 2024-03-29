# 스프링 MVC - 웹 페이지 만들기

---

## 타임리프를 이용한 SSR 웹 페이지

내추럴 뷰 템플릿으로 사용할 수 있는 타임리프의 기본 문법을 학습하고, 직접 SSR 웹 페이지를 제작한다.

- 순수 HTML을 그대로 유지하면서도 뷰 템플릿의 동적 반영을 구현할 수 있음.

### @ModelAttribute 사용

**등록**을 처리하는 메서드에서, 클라이언트가 요청한 HTTP 바디 데이터를 `@ModelAttribute` 를 이용해 조회 및 반환할 수 있다.

- 템플릿 측에서 사용하는 `name`값을 지정해줄 수도 있고, 생략할 수도 있다.

- 네이밍 룰이 지켜진다면 `Model` 을 직접 세팅하거나 반환하는 작업도 생략 가능하다.

### 리다이렉트 처리

스프링은 뷰 템플릿을 내부적으로 호출하는 대신, **리다이렉트** 처리를 구현하기 위해 `redirect:/...` 의 형식을 지원한다.

- redirect URL은 하드코딩할 수 있는데, 보다 나은 방법은 `RedirectAttribute`를 사용하는 방식.

---

## PRG Post/Redirect/Get

**등록**을 처리하는 메서드가 단순 POST 처리 및 뷰 템플릿 반환으로 작성된 경우, 웹 브라우저의 **새로고침** 버튼 클릭만으로 **중복 등록**을 유발할 수 있는 보안취약점이 발생한다.

- 화면 상에서는 등록 버튼 클릭 이후 화면이 전환됐지만,

- 서버 입장에서는 클라이언트의 마지막 요청이 **등록**을 위한 POST 요청이었기 때문.
  
  - 뷰 템플릿 간 호출은 서버 내부 프로세스일 뿐, 클라이언트의 2차 요청이 아니다.

이를 해결하기 위해서는 **PRG 패턴**을 적용해야 한다.

- **등록**에 해당하는 메서드는 처리 완료 후 **리다이렉트** 처리를 통해 별도의 페이지를 **GET** 요청하도록 구성하는 것.

- 이 경우 POST 요청의 결과물은 **302** 로 돌아가게 되고,

- 클라이언트가 별도의 페이지를 또다시 **GET** 요청으로 받아오기 때문에,

- 새로고침 버튼에 대해서도 안전할 수 있다.

---

## RedirectAttributes

리다이렉트 URL을 String 합치기로 하드코딩하는 것 또한 보안취약점이 상당하다.

`RedirectAttribute` 클래스를 활용해서, URL을 동적으로 생성하는 방식을 활용하자.

- 특히 **PRG 패턴**이 적용된 케이스에 대해.

- 별도의 status 값을 함께 활용하여 클라이언트 단에서도 화면을 조절하면 완벽하다.


