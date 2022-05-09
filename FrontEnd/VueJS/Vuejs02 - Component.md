# Vuejs02 - Component

---

# Component

## 컴포넌트 (Component)

Vue의 가장 강력한 기능 중 하나.

HTML element를 확장하여, 재사용 가능한 코드를 **캡슐화** 한다.

Vue Component는 Vue Instance이기도 하므로, 모든 옵션 객체를 사용할 수 있고 **Life Cycle Hook**도 사용 가능하다.

전역과 지역 컴포넌트로 구분 가능하다.

## 전역 컴포넌트 등록

`Vue.component(tagName, options)`를 사용

컴포넌트의 이름은 케밥 표기법이나 파스칼 표기법을 사용함

선언된 컴포넌트는,

- Vue Instance를 사용하는 HTML에서 **케밥 표기법을 활용한 이름의 태그로** 붙여넣을 수 있음.

- MyGlobal로 선언한 component를, `<my-global>`로 붙일 수 있음.

## 지역 컴포넌트 등록

컴포넌트를 el이 등록된 Vue Instance에서 `components: {}`의 **인스턴스 옵션으로 등록**함으로써 해당 인스턴스 범위해서만 사용 가능하도록 만들 수도 있다.

## Component Template

Component는 하나의 완성된 기능이 들어있는 HTML+JS 덩어리.

Component 선언 후 세부 사항을 설정할 때, view page에 해당하는 HTML은 `template:` 속성으로 설정하게 된다. 거기에서 백틱으로 HTML을 직접 써넣는 방식이 가능.

당연히 이걸 따로 빼고 싶을 수 있는데,

- script 바깥의 html에 `<template>` 태그를 두고 **반드시** 그 안의 `<div>` 에서 사용하고자 하는 HTML을 만든 뒤

- template 태그에 id를 설정하고 component 속 template는 해당 id를 참조하면 (`"#mycomp"` 이런 식) 된다.

- template의 root 레벨에서 div가 아닌 다른 태그를 사용해 시작하거나, div를 두 개 이상 만들면 error가 발생한다.

## Component 간 data 공유 문제

여러 개의 component를 사용하는 가운데 전역변수를 사용하면, 분리되어야 할 component들 간 데이터가 완전히 동기화되는 문제가 발생한다.

- 해당 데이터를 component 내에서 직접 정의하는 것으로 지역화, 해결

---

# Component 간 통신

## 컴포넌트 간 통신

상위(부모) - 하위(자식) 컴포넌트 간 data 전달 방법이 중요.

- 부모에서 자식으로는, **props**라는 특별한 속성을 전달.

- 자식에서 부모로는, **event로만** 전달이 가능. (Emit)

## 상위에서 하위 컴포넌트로 data 전달

하위 컴포넌트는 상위 컴포넌트의 값을 직접 참조하는 것이 **불가능하다.**

하위 컴포넌트는, data와 비슷한 방식으로 상위 컴포넌트에서 전달된 **props** 속성의 값을 template에서 사용하게 된다.

## props

하위 컴포넌트에서 선언되는 property에 해당

객체의 형태로 여러 개의 prop이 등록될 수 있는데,

- 상위 컴포넌트로부터 입력되는 **type**과 **required 여부**를 prop의 property로 등록해주는 것이 바람직하다.

등록된 props는, html의 component 태그에서 **property**에 해당한다.

- 예를 들면 `<child-component :pdata="msg">`의 형태로 
  
  - 하위 component를 사용할 때
  
  - *pdata*로 등록된 props를 사용하고
  
  - pdata의 값으로는 상위 component로부터 전달된 msg를 binding하는 식.

## 렌더링 과정

1. new Vue()로 상위 컴포넌트인 인스턴스를 생성한다.

2. Vue.component()를 이용해, 하위 컴포넌트인 child-component를 생성한다.

3. 둘은 부모와 자식 관계가 성립한다.

4. 하위 컴포넌트에 props 속성을 정의한다.

5. html에 하위 컴포넌트의 태그를 추가한다.

6. 하위 컴포넌트에 v-bind 속성을 사용하면, 상위 컴포넌트의 data의 key에 접근 가능하다.

7. 상위 컴포넌트의 msg 속성값이 String 값이 하위 컴포넌트의 props로 전달된다.

8. 하위 컴포넌트의 template 속성에 정의된 {{}}로 전달된다.

## 동적 props

v-bind를 사용해 부모의 데이터에 props를 동적으로 바인딩하는 방식

데이터가 상위에서 업데이트될 때마다 하위 데이터로 전달된다.

- 위에서 사용한 방식이기도 함.

- v-for를 이용하는 방식도 숙지

## 객체의 속성 전달 props

오브젝트의 모든 속성을 전달할 경우, `:prop-name` 대신 `v-bind` 만 작성함으로써 **모든 속성을** prop으로 전달할 수 있다.

- id, title 속성을 가진 post라는 객체를 뒀을 때,

- `<blog-post v-bind="post">`로 id와 title을 모두 전달 가능



## 사용자 정의 이벤트 (Custom Events)

### 이벤트의 이름

컴포넌트 및 props와 달리 이벤트는 자동 대소문자 변환을 제공하지 않는다.

즉, 대소문자 혼용 대신 emit할 정확한 이벤트 이름을 작성하는 것이 권장됨

v-on 이벤트 리스너는 항상 자동으로 소문자 변환된다. 

따라서 케밥 표기법 사용이 권장됨

### 이벤트 발생 및 수신

버튼 click 등으로 메서드가 호출되면,

- `this.$emit()`으로 구체적인 이벤트를 발생시킨다.
  
  - 매개변수로 이벤트명과 파라미터를 집어넣을 수 있음

- `this.$on()으로 이벤트를 수신할 수 있다.
  
  - emit 시 선언한 이벤트명으로 받고, 파라미터도 사용할 수 있으며, **콜백함수** 형태로 받게된다.

## 하위에서 상위 컴포넌트로 event 전달

기본적으로 하위에서 상위 컴포넌트로는 event만 전달이 가능하므로, 중요하다.

하위 컴포넌트에서 **상위 컴포넌트가 지정한** 이벤트를 발생시키면 (emit)

상위 컴포넌트는 하위 컴포넌트가 발생시킨 이벤트를 수신 (on)하여 data를 처리하는 구조.

- 하위 컴포넌트에서 `this.$emit('이벤트명')`으로 이벤트 발생

- `<child v-on:이벤트명="상위컴포넌트 메서드명">`으로 이벤트 수신


