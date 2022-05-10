# Vuejs03 - axios, router

---

# axios

## HTTP 통신: axios

Vue에서 권고하는 HTTP 통신 라이브러리.

**Promise** 기반의 HTTP 통신 라이브러리. 상대적으로 다른 라이브러리들에 비해 문서화가 잘 되어있고 API가 다양하다.

- `axios.get(URL)` 하면 Promise 객체를 return한다.

- then, catch, finally 사용이 가능하다.

> Promise는?
> 
> 서버에 데이터를 요청하여 받아오는 동작과 같이, **비동기 로직 처리**에 유용한 자바스크립트 라이브러리이다.
> 
> 자바스크립트는 단일 스레드로 코드를 처리하므로 특정 로직의 처리가 끝날 때까지 기다려주지 않는다. 따라서 데이터를 요청하고 받아올 때까지 기다렸다가 화면에 나타내는 로직을 실행해야할 때 Promise를 활용한다.

## axios API

### .get("URL").then().catch().finally()

해당 URL 주소로 HTTP GET 요청을 보낸다.

`then()`은 정상 실행 시 정의된 로직

`catch()`는 오류 발생 시 정의된 로직

`finally()`는 항상 실행

## .post("URL")

해당 URL 주소로 HTTP POST 요청을 보낸다.

then~catch~finally는 동일함

### axios({config})

HTTP 요청에 대한 자세한 속성들을 직접 정의하여 보낸다.

URL, 요청방식, 데이터 유형 등.

- 과거에 사용하던 jQuery AJAX와 비슷한 형태

### 그 외


