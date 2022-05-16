# Vuejs05 - Vuex

---

# Vuex

## vuex

Vue.js application에 대한 **상태관리패턴**과 **라이브러리**가 합쳐진 것

application 모든 component들의 **중앙 저장소** 역할을 하면서 데이터를 관리함

상위와 하위의 단계가 많이 복잡해진 경우 데이터 전달하는 부분 (pass prop, emit event)이 매우 복잡해지는 것을 보완

## 컴포넌트 간 data 전달

동일 부모를 공유하는 자식 컴포넌트 간 data 전달이 매우 불편하다는 점.

- **Event Bus**라는 것을 이용하는 방법이 있기는 하지만, 데이터의 일관적인 흐름을 파괴해서 유지보수가 너무 어렵다.

## vuex에서는?

vuex 저장소는 **single state tree**이다.

여러 컴포넌트에 직접 연결되어 있고, 해당 컴포넌트 간 부모자식 관계는 무시된다.

## 상태관리패턴

### Vuex 이전

> View -> Actions -> State -> View -> ...

상태(State)는 앱을 작동시키는 원본 소스

- Vue Instance의 data가 대표적

뷰(View)는 상태의 선언적 매핑

- template

액션(Actions)은 뷰에서 사용자 입력에 대해 반응적으로 상태를 바꾸는 방법

- method

이와 같은 단방향의 데이터 흐름

### Vuex의 경우

> Vue Components, Actions, Mutation, State로 이어지는 단방향 데이터 흐름
> 
> 그 사이의 Dispatch, Commit, Mutate, Render



---

# Vuex 핵심컨셉

## Vuex 구성요소

### State

Vuex는 **Single State Tree**를 사용한다. (단일 상태 트리)

중앙에서 관리하는 모든 상태 정보를 **State**가 관리한다.

- 일반 Vue Component의 data에 해당

여러 컴포넌트 내부에 있는 특정 State를 중앙에서 관리함.

- vuex 이전에는 state를 찾기 위해 각 컴포넌트들을 직접 확인해야 했음

- vuex를 활용하면, Vuex Store에서 컴포넌트에서 사용하는 state를 한 눈에 파악 가능

**Mutations**에 정의된 method에서만 state를 변경할 수 있음.

State가 변경되면, 해당 state를 공유하는 모든 컴포넌트의 DOM은 **자동으로 렌더링 됨**

- **computed**와 유사하다고 볼 수 있음

모든 Vue 컴포넌트는 Vuex Store에서 state 정보를 가져와 사용

각 컴포넌트는 `dispatch()`를 사용하여, Actions 내부의 method를 호출

### Actions

컴포넌트에서 `dispatch()` method에 의해 호출됨

백엔드 API와 통신하여, Data Fetching 등 작업을 수행.

- 주로 **비동기 작업**을 포함함

항상 **context**를 인자로 담아서 작업할 수 있음.

- store.js 파일 내에 있는 모든 요소의 변경 및 호출이 가능하지만,

- 직접 state를 변경하는 것은 **권장하지 않음**

Mutations에 정의되어 있는 method를 `commit()` method로 호출

State는 반드시 Mutations가 가진 method로만 조작할 필요.

- 서비스 규모가 커지더라도 state 관리를 올바르게 하기 위함.

- Controller, Service, DAO를 나눴던 것을 떠올리면 된다.

### Mutations

Actions에서 `commit()` method에 의해 호출됨

Actions에서는 비동기적 작업을, Mutations에서는 **동기적 작업만을** 함.

- 비동기적 작업이 있을 경우 state의 변화 시점이 명확하지 않기 때문

Mutations에 정의하는 method의 인자로는 항상 state를 넣어줄 수 있음

### Getters

**Computed**와 유사.

- **종속적인** 데이터.

State를 **변경하지 않고** 활용하여 계산을 수행

즉, Getters 자체가 state를 변경시키는 것은 아니다.

---

# Vuex 단계별 이해

## state

state는, 저장소에서 data 속성의 역할.

application에서 공유해야 할 data를 관리한다.

접근하는 방식은,`this.$store.state.data_name`

- Store를 중간에 두고 컴포넌트 간의 변경과 GET이 일어난다.

### 달라지는 부분

기존 부모 자식 컴포넌트 간의 data와 props, event를 주고받던 것이 전부 사라지고,

store.js에 정의된 state로부터 그와 연결된 컴포넌트가 직접 data를 받아오는 게 가능

## Getters

component에서 vuex의 state에 직접 접근하는 코드가 중복되는 문제를 해결하기 위한 기법.

Store의 state를 참조하는 Getters를 활용한다.

`this.$store.getters.computed_name`으로 접근.

- 주로 **state를 연산해놓은 값**을 자식 컴포넌트에서 get해올 때 사용.

- 특성 상 **computed를 활용할 때의 맥락과 거의 유사**

- 실제 활용 시에도 해당 getter를 활용하는 컴포넌트의 `computed` instance에서 선언되는 편이다.
  
  - ex) `return this.$store.getters.countMsg;`

## mapGetters

getters를 보다 간단하게 호출하기 위한 기법.

store.js에 `getters: {}`를 선언한 이후,

개별 컴포넌트에서 computed  Instance에서 `...mapGetters` 를 선언해 그 이름을 명시함으로써 연동한다.

- ES6의 spread syntax를 활용하고 있는 것.

- Getter로 만들어놓은 computed 속성의 값이 여럿 사용될 때 유용할 수 있다.

기존의 `this.$store`로 시작하는 getter 호출구문은 필요하지 않게 된다.

## Mutations

Store의 값을 변경하기 위해 사용.

각 컴포넌트에서 State의 값을 직접 변경하는 것은, *가능하긴 하지만* 권장하지 않는 방식이다.

- Mutations의 중계를 거치는 것이 추천됨.

이 때 Mutations는 직접 호출되지 않고, `this.$store.commit('정의된 메서드')` 으로 mutations이 가진 메서드가 호출된다.

- store.js에 있는 mutations의 메서드들은,
  
  - `state`를 첫 인자로 항상 가지게 되고
  
  - 컴포넌트로부터 `payload`를 전달받아 인자로 가질 수도 있다.
  
  - 객체를 payload로 전달하는 것도 가능하다.

주로 State 값의 추적을 위해 **동기적 기능**에 사용됨.

### mapMutations

mapGetters와 마찬가지로 `this.$store.commit()`까지 쓰지 않고도 mutations를 호출하기 위해 사용.

- mutations를 활용하는 방식은, 기존 JS에서 단순히 **함수를 호출하던 것**과 거의 유사하지만 문법이 달라서 혼동을 유발

- 이를 교정하기 위한 방법.

`...mapMutations`를 선언하고, 거기에 mutations에서 가지는 method의 이름과 method의 alias를 선언한다.

그리고 컴포넌트의 로직에서는 해당 alias를 단순 함수처럼 호출하면 mutations로 연결되는 구조.

## Actions

**비동기 작업의 결과를** 적용하려 할 때 사용

- 백엔드에서 데이터를 얻어오거나 하는 과정은 비동기이기 때문에, Mutations에서 이를 처리하다가 자칫 State를 잘못 건드릴 수 있다.

- 그래서 actions를 분리.

Mutations는 주로 상태 관리를 위해, **동기적**으로 처리하고 **비동기적**인 처리는 Actions가 담당함.

Actions에서 비동기 로직의 처리가 종료되면 비로소 Mutations를 호출해, 최종적으로 state를 제어함.

- 앞서 다뤘던 `async`, `await` 등을 사용할 수 있을 것.

- actions 로직 처리가 완료되면, `this.$store.dispatch()`로 mutations를 호출하게 된다.

### mapActions

마찬가지로 `...mapActions`를 사용 가능하다.
