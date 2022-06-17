# Part 4: 매일 자바와 함께

---

# Chapter 11: null 대신 Optional 클래스

---

## 값이 없는 상황을 어떻게 처리할까?

```java
public class Person {
    private Car car;
    public Car getCar() {
        return car;
    }
}

public class Car {
    private Insurance insurance;
    public Insurance getInsurance() {
        return insurance;
    }
}

public class Insurance {
    private String name;
    public String getName() {
        return name;
    }
}
```

- 사람, 차, 보험을 각기 클래스로 등록할 때, 각 객체 간 의존관계가 존재하는 상황

```java
public String getCarInsuranceName(Person person) {
    return person.getCar().getInsurance().getName();
}
```

- `person`이 차를 가지고 있지 않다면, (객체 생성 당시 필드값인 Car를 넣어주지 않았다면) `getCar()`는 null을 반환한다.

- null에 대해 `getInsurance()`를 실행시켰으니, `NullPointerException`이 발생한다.

- 그 외에도 Person 자체가 null이거나 Insurance가 null을 반환하는 경우들도 발생 가능하다.

### 보수적인 자세로 NullPointerException 줄이기

직접 null을 확인하는 코드들을 추가해서 문제를 해결하는 방식이 있다.

#### 깊은 의심

```java
public String getCarInsuranceName(Person person) {
    if(person != null) {
        Car car = person.getCar();
        if(car != null) {
            // 이하 생략
    return "Unknown";
}
```

- 최소한 Exception은 발생하지 않을 수 있지만, 모든 변수에 대해 null을 의심해야 하므로 코드의 들여쓰기 수준이 증가한다.

- 이러한 반복 패턴 코드를 **깊은 의심**이라고 부른다.

- 코드 구조가 엉망이 되고, 가독성도 떨어진다.

#### 너무 많은 출구

```java
public String getCarInsuranceName(Person person) {
    if(person == null) {
        return "Unknown";
    }
    Car car = person.getCar();
    if(car == null) {
        return "Unknown";
    }
    // 생략
    return insurance.getName();
}
```

- 중첩 if 블록은 없어졌지만, 메서드에 출구가 너무 많아서 유지보수가 어렵다.

- Unknown이라는 문자열을 반복적으로 반환하는데, 오타 등 실수를 유발할 수 있다.

#### 소결

이러한 예시들은, *값이 없다는 사실*을 표현함에 있어 `null`을 사용하는 것이 좋은 방법이 아니라는 점을 보여주고 있다.

### null 때문에 발생하는 문제

#### 에러의 근원이다

`NullPointerException`은 너무 흔하다.

#### 코드를 어지럽힌다

위 예시에서 명확하게 보여준다.

#### 아무 의미가 없다

말 그대로 아무 의미가 없는 값이다. **정적 형식 언어**에서 적절하지 못하다.

#### 자바 철학에 위배된다.

자바는 개발자로부터 **모든 포인터를 숨겼다**. 근데 null 포인터가 유일한 예외다.

#### 형식 시스템에 구멍을 만든다.

기본적으로 무형식으로 정보를 포함하고 있지 않기 때문에, **모든 참조 형식에 null을 할당할 수 있다.**

이러다보면 시스템에 null이 퍼져나갔을 때 해당 null이 어떤 의미로 사용되었는 지 절대 알 수 없다.

### 다른 언어는 null 대신 무얼 사용하나?

#### 그루비의 안전 내비게이션 연산자

그루비는 `?.`를 도입하여 null 문제를 해결하고 있다.

```groovy
def carInsuranceName = person?.car?.insurance?.name
```

- 중간에 null이 할당되어 있어도, `?`가 있기 때문에 null을 참조하지 않고 진행된다.

- 호출 체인에 null인 참조가 있으면 결과적으로 null이 반환된다.

자바에서도 비슷한 제안이 있었지만, 대체로들 null을 체크하는 if문을 사용하고 있었기 때문에 채택되지 않았다.

- 하지만 이는 null 문제의 본질을 해결하는 것이 아니라, **문제를 미루고 숨기는 방식이다.**

#### 하스켈, 스칼라의 선택형값

하스켈은 `Maybe`라는 형식을 제공한다.

- `Maybe`는 주어진 형식의 값을 갖거나, 아무 값도 갖지 않을 수 있어서 null 참조 개념이 자연스럽게 사라진다.

스칼라도 `Option[T]`라는 구조를 제공하여, T 형식의 값을 갖거나 아무 값도 가지지 않을 수 있다.

- `Option` 형식에서 제공하는 연산을 사용해, 값이 있는지 여부를 명시적으로 활용해야 하기 때문에 (null 체크) null 관련 문제가 일어날 가능성이 줄어든다.

#### 자바에서는?

Java 8은 선택형값 개념의 영향을 받아, `java.util.Optional<T>`라는 새로운 클래스를 제공한다.

## Optional 클래스 소개

`Optional`은 **선택형값을 캡슐화하는 클래스**다.
