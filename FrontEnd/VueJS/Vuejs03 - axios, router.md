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

### promise 객체

`axios.get("URL")`은 promise 객체를 만들어낸다. 만들어진 promise 객체는 then 등의 작업을 수행하기 위해 pending 상태 (대기)에 놓인다.

### 연속작업

비동기 처리 시 데이터를 호출해 받고 그걸 다시 처리해서 다른 데이터를 호출하는 식의 연속 작업이 다수 일어난다.

promise 객체에 대해서는, `.then`을 연속적으로 **chaining**함으로써 이러한 처리가 가능하다.

### async, await

연속작업 시 `.then`을 양산하지 않는 다른 방법으로, `async`와 `await`를 활용할 수 있다.

- axios를 호출할 함수를 `async function`으로 선언하고,

- axios를 호출하거나 return된 데이터를 처리하는 작업에 대해 `await`를 붙여주면 된다.

---

# vue-router

## vue-router

라우팅은, **웹 페이지 간의 이동 방법**이다.

vue-router는 Vue.js의 공식 라우터로, 라우터는 **컴포넌트와 매핑된다.**

이는 Vue를 이용해서 **SPA**를 제작할 때 유용하다.

- URL에 따라 컴포넌트를 연결해, 설정된 컴포넌트를 보여주는 방식으로 제작한다.

## 설치

Vue.js 2.x버전을 사용하는 경우, CDN을 활용할 시 unpkg.com에서 router의 버전을 명시해야 한다.

## 연결

`new Vue({})`에서 Vue Instance들을 선언했던 것과 비슷하게,

`new VueRouter({})`에서 `routes: []` 옵션을 두고 라우팅될 **컴포넌트**들을 정의한다.

- 라우팅의 대상은 **컴포넌트**다.

- `routes:` 에서 정의되는 라우터들은 `path`와 `component`를 속성으로 가지는 객체들이다.

- template 등 여러 로직을 가지고, 나중에는 js파일로 분리된다.

## vue-router 이동 및 렌더링

네비게이션을 위해, `<router-link to="">` 를 사용한다.

이는 HTML 상에서 a 태그로 렌더링되는데, `to` 속성에 앞서 설정한 라우터의 **path**를 넣어 연결하게 된다.

연결된 라우터는 HTML 상에서 `<router-view>`가 선언된 지점에 로딩된다.

- 보통 `VueRouter`는 별도의 js 파일로 분리되고, HTML 에서는 `<script type="module">`에서 src로 연결된다.

- `VueRouter`를 정의한 파일에서는 기능별로 분리되어 있는 컴포넌트 js 파일들을 import해서 path와 component를 매핑하게 된다.

## `$router`, `$route`

특정 라우터가 호출된 이후,

- `this.$route`는 현재 호출된 라우터의 정보를 보여준다.
  
  - URL에 쿼리나 path 파라미터를 넣어놓은 경우 그걸 갖다쓰기 위한 방법.
  
  - `this.$route.params`나 `.path`로 사용한다.

## 이름을 가지는 라우트

라우트는 연결하거나 탐색을 수행할 때, 이름이 있는 라우트를 사용할 수 있다.

Router Instance를 생성할 때 옵션으로 name을 지정할 수 있다.

- `<router-link>`의 to 옵션을 설정할 때 path가 아닌 `name: 이름` 의 형태로 라우터를 지정할 수 있는 것

## 프로그래밍 방식 라우터

`<router-link>`를 사용하여 선언적 네비게이션용 anchor 태그를 만드는 것 외에도, 

라우터의 instance method를 사용해 프로그래밍으로 수행할 수 있음.

- `<a href="#boardview" @click="$router.push({name: "boardview", params: { no: 3 } })">`

- 이런 식으로 a 태그에서 라우터 동작을 직접 구체적으로 지정할 수 있음.

## 중첩된 라우트

앱 UI는 일반적으로 여러 단계의 중첩된 컴포넌트 구조를 가진다.

- /board 하위에 /write, /modify, /delete 같은 URL이 나열되는 것을 생각하면 됨.

이를 구현하기 위해, `VueRouter`를 설정한 js 파일에서 개별 routes를 설정할 때 `children: []` 프로퍼티를 둠으로써 라우터를 중첩시킬 수 있다.

- 중첩된 라우트를 구현하면서도 가장 부모가 되는 라우터의 path가 호출될 경우에 대한 redirect도 설정할 수 있다.
