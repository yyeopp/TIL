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


