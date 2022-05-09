# Vuejs01 - Instance

---

# Vue.js

## 개요

Google에서 Angular로 개발하다가, 가벼운 걸 만들어 보고 싶은 생각으로 시작한 개인 프로젝트

사용자 인터페이스를 만들기 위해 사용하는 오픈소스 Progressive Framework

## MVVM Pattern

**Model + View + ViewModel**

- Model: 순수 자바스크립트 객체

- View: 웹페이지의 DOM

- ViewModel: Vue의 역할

기존에는 자바스크립트로 view에 해당하는 DOM에 접근하거나 수정하기 위해 jQuery와 같은 library를 이용했다.

Vue는 view와 Model을 연결하고 **자동으로 바인딩**하므로, **양방향 통신**을 가능하게 한다.

---

# Vue Instance

## 생성

`<script>` 태그 안에, `new Vue({})` 의 형태로 instance를 생성한다.

내부에서 속성들을 정의하게 된다.

### 속성

#### el

Vue가 적용될 요소를 지정함. 주로 HTML Element.

#### data

Vue에서 사용되는 정보를 저장. 객체 또는 함수

#### template

화면에 표시할 HTML, CSS 등 마크업 요소를 정의하는 속성. view의 데이터 및 기타 속성들도 함께 화면에 그릴 수 있다.

#### methods

화면 로직 제어와 관계된 method를 정의하는 속성.

마우스 클릭 이벤트 처리와 같이 화면의 전반적인 이벤트와, 화면 동작과 관련된 로직 추가

#### created

뷰 인스턴스가 생성되지마자 실행할 로직을 정의

## Vue Instance의 유효범위

생성 시 HTML의 특정 범위 안에서만 옵션 속성들이 적용된다.

el 속성과 관계가 밀접함

### 주요 과정

- Vue()로 인스턴스가 생성됨 **created**

- el 속성에 지정한 화면요소(DOM)에 인스턴스가 부착됨 **mounted**

- el 속성에 인스턴스가 부착된 후, data 속성이 el 속성에 지정한 화면 요소와 그 이하 레벨의 화면 요소에 적용되어 값이 치환됨. **updated**

---

# Vue Instance Life-Cycle

## Life-Cycle

크게 나누면,

- Instance의 **생성**

- 생성된 Instance를 화면에 **부착**

- 화면에 부착된 Instance의 내용이 **갱신**

- Instance가 제거되는 **소멸**

의 4단계.

### Life Cycle Hooks

#### beforeCreate

Vue Instance가 생성되고 각 정보의 설정 전에 호출.

DOM과 같은 화면요소에 접근 불가

#### created

Vue Instance 생성 후, 데이터 설정이 완료된 후 호출

Instance가 화면에 부착되기 이전이므로 template 속성에 정의된 **DOM 요소는 접근 불가**

**서버에 데이터를 요청 (REST API 등 http 통신)하여 받아오는 로직**을 수행하기에 좋다.

#### beforeMount

마운트가 시작되기 전 호출

#### mounted

지정된 element에 Vue Instance 데이터가 마운트된 이후에 호출

template 속성에 정의한 화면 요소에 접근할 수 있어, **화면 요소를 제어하는 로직 수행**

#### beforeUpdate

데이터가 변경될 때 virtual DOM이 렌더링. 패치되기 전에 호출

#### updated

Vue에서 관리되는 데이터가 변경되어 **DOM이 업데이트 된 상태**

**데이터 변경 후 화면 요소 제어와 관련된 로직**을 추가

#### beforeDestroy

Vue Instance가 제거되기 전 호출

#### destroyed

Vue Instance가 제거된 후 호출

---

# template - 보간법 interpolation

## 보간법(Interpolation)

### 문자열

데이터 바인딩 기본 형태는 **Mustache**구문을 사용한 텍스트 보간

- `{{ 속성명 }}` 의 형태

`v-once` 디렉티브는 데이터 변경 시 업데이트 되지 않는 일회성 보간을 수행한다.

### 원시 HTML

이중 중괄호는 HTML이 아닌 일반 텍스트로 데이터를 해석하기 때문에,

실제 HTML을 집어넣고 출력하고자 한다면 `v-html` 디렉티브를 사용한다.

## JavaScript 표현식 사용

Vue.js는 모든 데이터 바인딩 내에서 JS 표현식의 모든 기능을 지원한다.

- 사칙연산, 삼항 연산자, `.split()`이나 `reverse()`같은 메서드 호출

단, 각 바인딩에 하나의 단일 표현식만 포함될 수 있다.

- 변수 선언이나 `if`문 은 사용할 수 없다.

---

# template - Directive

## 디렉티브(Directives)

디렉티브는 v- 접두사가 있는 **특수 속성**이다.

디렉티브 속성값은 단일 JS 표현식이 된다.

- 단 `v-for`는 예외

디렉티브의 역할은, 표현식의 값이 **변경될 때** 사이드 이펙트를 **반응적으로 DOM에 적용**하는 것이다.

### v-model

양방향 바인딩 처리를 위해 사용

- 주로 form의 input이나 textarea에서.

### v-bind

엘리먼트의 속성과 바인딩 처리를 위해 사용

- 자주 사용하기 때문에 약어를 지원. `:` 로 사용 가능

- 예시: Vue에서 data로 `idValue: "test-id"`를 선언해놓은 상태에서,
  
  div에서 `:id="idValue"`라고 쓰면 해당 div의 id가 `test-id`로 설정된다.

### v-show

조건에 따라 엘리먼트를 화면에 렌더링.

- v-show에 바인딩되는 변수를 boolean으로 잡아둔다.

- false일 때 해당 div가 자동으로 `diplay: none`이 됨.

- 즉, **일단 전부 렌더링하고 보여주지만 않는 방식**

### v-if, v-else-if, v-else

조건에 따라 엘리먼트를 화면에 렌더링한다.

사용법은 원래 알던 그 if문과 동일함

v-show와 다른 점은,

- false일 때 렌더링 자체가 이루어지지 않고

- true이던게 false로 변하면 엘리먼트가 아예 삭제되며

- template와 v-else가 지원된다는 것.

### v-for

배열이나 객체 반복에 사용

`v-for="요소변수이름 in 배열"` 이나

`v-for="(요소변수이름, 인덱스) in 배열"`의 형식으로 사용

- 인덱스를 활용하지 않아도 된다.

- 배열이 **객체의 배열** 이여도 된다.
  
  - `객체.속성` 의 문법을 그대로 활용하면서 순회가 가능

### v-cloak

Vue Instance가 준비될 때까지 mustache 바인딩을 숨기는 데 사용

`<style>` 태그 내에서 `[v-cloak]::before {}` , `[v-cloak] > * {}` 같은 **CSS** 규칙으로 사용됨.

Vue Instance가 준비되면 v-cloak은 제거됨

---

# Vue Instance 속성

## Vue method

`<script>` 태그 내의 Vue instance에서는 생성과 관련된 data와 method를 정의할 수 있다.

`methods: {}` 로 함수들을 선언하고, instance 내의 data를 `this.data명`으로 접근할 수 있다.

html의 `{{}}`에서 Vue의 메서드를 호출할 수도 있는데, 이 때는 메서드 이름에 `()`까지 붙여줘야 함수 호출로 인식된다.

## Vue filter

뷰의 필터는 화면에 표시되는 텍스트의 형식을 쉽게 변환해주는 기능이다.

데이터 출력의 format이 필요할 때 유용한 기능

`Vue.filter({})`에 원하는 기능을 구현해두고, html의 `{{}}`에서 filter 처리할 메시지와 사용할 필터를 `|` 연산자로 명기해두면 사용가능하다.

전역필터, 지역필터 모두 선언 가능하고 필터끼리 chaining할 수도 있다.

## Vue computed

특정 데이터의 변경사항을 실시간으로 처리한다.

**캐싱**을 이용하여, 데이터의 **변경이 없을 경우** 캐싱된 데이터를 반환한다.

- 같은 변수를 method로부터 불러올 경우, 데이터의 변경이 없더라도 연산을 매번 실행한다는 점이 다르다.

Setter와 Getter를 직접 지정하는 것도 가능하다.

작성은 method 형태지만, Vue에서 **Proxy**처리를 해준 결과 **property** 처럼 사용이 이루어진다.

- Vue에 특정 data값이 존재하고, 화면에서 해당 data 값을 처리한 무엇인가를 출력해야 할 때
  
  - 새로운 변수를 data에 선언함이 없이 computed에서 함수의 형태로 data를 처리하여 필요한 값을 return하고
  
  - 그 값은 캐싱되어 계속 사용된다.
  
  - return되는 값들은 참조하고 있는 특정 data값에 **전적으로 종속되어 있기 때문에** 별도로 선언하지 않고 computed 시킬 수 있다.

## Vue watch

Vue Instance의 특정 property가 변경될 때 실행할 콜백 함수를 설정.

- 새로운 값과 이전 값을 매개변수로 활용할 수 있다.

Computed와 달리, ~했을 때 ~하라는 식의 **명령형 프로그래밍**.

- 특정 data 변경 시 이와 연계되는 다른 data를 변경하는 작업을 한다는 점에서, computed로 대체가 충분히 가능하다.

- 당장 computed와 달리 **변화하는 특정 data에 연계되어 화면에 표시되는 data**를 따로 선언해줘야 하는 차이점이 있다.

---

# Vue Event

## Vue event
