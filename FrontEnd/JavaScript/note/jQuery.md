# jQuery

## jQuery 소개

### jQuery 소개

- 2019년 기준으로는 대부분의 웹페이지가 jQuery를 사용했다.

- 사실 지금으로서는 사양 단계.



### jQuery 특징

- 크로스 플랫폼을 지원. 어떤 브라우저에서도 동작
  
  - 브라우저 호환성을 고려한 대체코드를 작성할 필요가 없다.

- 네이티브 DOM API보다 직관적이고 편리한 API를 제공해 개발 속도를 향상

- Event 처리, Ajax, Animation 효과를 쉽게 사용

- 다양한 effect 함수를 제공하여 동적인 페이지를 쉽게 구현.

- CDN에서 직접 사용할 수 있다.



### jQuery 기본구문

- Selector를 사용하여 DOM 객체를 탐색하고, 반환된 **래퍼세트**를 통해 함수를 수행한다.

- Selector 표현식과 Action 메서드를 조합한 형태로 구문을 작성한다.
  
  - ex) `$(selector).action();`

- 래퍼세트 객체는 **메서드 체인**을 제공하여, 메서드 호출에서의 반복적인 코딩을 줄여준다.

- jQuery로 DOM을 탐색하기 전에, 웹 브라우저에 문서가 모두 로드되어 있어야 한다.

- jQuery는 Document ready 이후 처리할 수 있는 두 가지 방법을 제공한다.
  
  - 자세한 방법 vs 간결하지만 덜 직관적인 방법.



## DOM 요소 선택 (Selector)

### jQuery에서의 DOM 탐색

- CSS에서 사용하는 Selector 표현 방식을 사용하고 있다.

- 브라우저가 표준 CSS 선택자를 올바르게 구현하지 않았어도 W3C 표준에 맞게 요소를 탐색

- jQuery는 DOM 탐색 결과로, **래퍼세트(Wrapper Set)** 라는 DOM을 래핑한 객체를 반환한다.

### 선택자

- 기본형식
  
  `$("h1").css("color","blue");` jQuery + selector + function

- *, #, "", . 같은 CSS 문법을 그대로 가져와서 selector를 표현할 수 있다.
  
  - 요소, ID, 클래스, 다중, 복합, 모두 지원함



### 래퍼세트와 메서드

- jQuery는 선택자를 통해 탐색한 DOM 객체들을 래퍼세트라는 특별한 배열 객체에 담아 반환한다.

- jQuery는 선택된 DOM 객체가 없는 경우에도 비어있는 래퍼세트 객체를 반환함

- 래퍼세트 객체에는 내포된 DOM 객체들을 처리하는 다양한 메서드가 있다.

- jQuery 메서드는 플러그인 확장을 통해 추가할 수 있다.
  
  - size(), get(index), index(element), add(expr), not(expr), each(function(index, element), filter() 등등
  
  - each는 반복문에 해당
    
    - `$.each(array, function(index, item) {});`
    
    - 배열이나 객체를 반복적으로 처리할 때 사용.

- 계층 구조를 탐색하는 메서드들도 있다. parent, children, prev, next 등등

- 위치기반 함수도 있다. eq, first, last 등등



#### 래퍼세트에 요소 추가/삭제

- 래퍼세트에 요소를 추가하거나 특정 요소를 삭제하는 메서드를 제공하고 있다.

- add() 메서드는 래퍼세트에 주어진 조건을 만족하는 요소를 추가한 새로운 래퍼세트를 반환함

- not() 메서드는 주어진 조건에 해당하는 요소를 제거한 새로운 래퍼세트를 반환함.

#### DOM 요소 판별

- is() 메서드는 기존 래퍼세트가 주어진 선택자와 일치하는지 여부를 반환 (boolean)

- find() 메서드는 모든 요소들에 대해 주어진 선택자를 만족하는 모든 **자손** 요소를 선택



## DOM 객체

### DOM 객체 제어

- 순수 JS만을 이용하여 DOM 객체 구조를 처리하는 것은 어렵고 성가시다.

- jQuery 메서드를 사용하면, DOM 객체를 보다 쉽게 다룰 수 있다.

- jQuery는 DOM 요소의 속성이나 class, style을 제어하는 DOM 특성제어 메서드를 제공
  
  - attr(), removeAttr(), addClass(), removeClass(), toggleClass(), css()
  
  - 그 외에도 DOM 내부제어 메서드, DOM 추가/삭제 메서드와 DOM 객체를 삽입하는 메서드가 있다.
    
    - html(), text() / $(), remove(), empty(), clone() / append() 등



### DOM 특성 제어

- 기존 JS에서 element 따오는 코드부터 길게 나열하던 코드를 획기적으로 줄일 수 있다.

- getter와 setter가 분리되어 있지 않고, 매개변수 개수가 1개인지 2개인지에 따라 구분한다.

### DOM 객체 삽입

- 이미 존재하는 DOM 객체에 다른 DOM 객체를 삽입하는 것

- Target 객체를 기준으로 어느 위치에 삽입하는지에 따라 다양한 메서드
  
  - append, prepend, after, before, apppendTo 등등

### Effect

- 화면에서 보여주는 시각 효과를 구현하는 방법.

- 사용자가 직접 애니메이션 효과를 만들 수 있다.
  
  - show(), hide(), toggle(), slideDown(), slideUp(), fadeIn(), fadeOut(), animate() 등등



## jQuery Event

### DOM Event 처리

- 기존 JS DOM event를 간편하게 처리, 연결 가능

- 이벤트 핸들러를 할당, 해제할 수 있는 통합 메서드 제공



### Event Binding

- bind() 함수. 선택된 DOM 객체의 이벤트에 지정한 핸들러를 연결하는 함수

- **동적으로 생성한 DOM 객체에는 적용할 수 없다.**

- unbind() 함수는 객체 이벤트에 지정한 핸들러를 지운다.

- on() 함수는 bind()와 마찬가지로 DOM 객체에 이벤트 핸들러를 연결하지만,
  
  - **동적으로 생성한 DOM 객체에도 적용이 가능**하다는 특징
  
  - 이벤트 연결에 가장 기본이 되는 함수로 **권장**되고 있다.

- off() 함수는 DOM 객체의 이벤트를 제거
  
  - 선택된 DOM 객체의 특정 또는 모든 이벤트를 제거할 수 있다.

- Simple event bind도 가능하다. on 자리에 click을 직접 쓰는 식

- one() 함수는 이벤트를 연결 후 한번만 실행, 삭제하는 기능
  
  - on(), 실행, off()가 묶여있다.
