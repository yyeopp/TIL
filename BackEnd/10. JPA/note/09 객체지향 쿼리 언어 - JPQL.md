# 객체지향 쿼리 언어

---

## 소개

JPA는 `em.find()` 외에도 다양한 쿼리 방법을 지원한다.

대표적인게 JPQL이고,

- JPA Criteria, QueryDSL, 네이티브 SQL

- JDBC API 직접 사용 등

### JPQL

JPA를 사용하면 엔티티 객체 중심으로 개발이 가능하지만,

**검색 쿼리**에 대해서는 처리가 꽤 어렵다.

애플리케이션이 필요로 하는 데이터만 DB에서 불러오려면, **검색 조건이 포함된 SQL**이 결국 필요하다.

#### JPQL은?

SQL을 추상화한 **객체 지향 쿼리 언어**이다.

ANSI 표준 SQL은 모두 지원하며, 테이블이 아닌 **객체를 대상**으로 쿼리를 생성할 수 있다.

### Criteria

Java 코드로 JPQL을 작성하는, 일종의 JPQL 빌더.

- JPA 기본 스펙에 해당함

동적 쿼리를 생성하기에 적합한 스펙이지만, **코드가 지나치게 복잡해서 실무적으로 거의 사용하지 않는다**.

- QueryDSL이 보다 보편적이다.

### QueryDSL

마찬가지로 Java 코드로 JPQL을 작성하는 JPQL 빌더.

- JPA 기본 스펙이 아닌 **오픈소스 프로젝트**

컴파일 시점에 문법 오류를 찾을 수 있고, 동적 쿼리를 작성하기에 편리함

Criteria 대비 훨씬 단순하고 쉬워서 실무 사용이 권장됨

### 네이티브 SQL

SQL을 직접 날리는 기능. JPQL에서 해결이 안 되는 DBMS 의존적 dialect SQL이 필요할 때 주로 사용

### JDBC 직접 사용, SpringJdbcTemplate 등

경우에 따라 JDBC 커넥션이나 mybatis 까지도 바인딩해서 사용할 수 있다.

- JPQL과 달리 자동으로 `flush` 처리를 안 해주기 때문에, insert 이후 select 하고자 하는 경우 강제 `flush` 처리가 필요

---

## JPQL - 기본 문법과 쿼리 API

Java Persistence Query Language

### 소개

**객체지향 쿼리 언어**로, 엔티티 객체를 대상으로 쿼리한다.

특정 DBMS에 의존하지 않는다.

### 문법

- 엔티티와 속성은 **대소문자 구분이 필요**

- JPQL 키워드는 대소문자 구분 불필요

- 테이블 이름이 아니라, **엔티티의 이름**을 사용

- **별칭이 필수**

#### TypeQuery, Query

반환 타입이 명확한지 여부에 따라 결정

#### 결과 조회 API

- 결과가 하나 이상일 시, `getResultList()`
  
  - 결과가 없으면 **빈 리스트 반환**

- 결과가 정확히 하나일 시, `getSingleResult()`
  
  - 결과가 무조건 하나 있어야 한다.
  
  - 결과가 없거나 다건이면 바로 Exception 발생

#### 파라미터 바인딩

`:username` 이런 식으로 Query 객체를 생성한 뒤,

`setParameter()` 메서드로 바인딩한다.

- 메서드 체이닝으로도 사용할 수 있다.

- 이름 기준으로 바인딩하는 게 일반적이고, 위치 기준도 지원하기는 한다.

---

## 프로젝션

SELECT 절에서 **조회할 대상을 지정**하는 것

- 프로젝션의 대상 : 엔티티, 임베디드 타입, 스칼라 타입

- DISTINCT 사용 가능함

프로젝션의 대상이 된 엔티티는 모두 **영속성 컨텍스트에 반영**된다.

- `List` 안에 들어있는 엔티티 모두가 반영되어, 변경이 감지된다.

### 여러 값 조회

프로젝션 대상으로 여러 개의 필드를 지정한 경우, 여러 방법으로 결과값을 받아올 수 있다.

- `Query` 타입으로 조회하고 `Object[]` 타입으로 받기

- `new` 명령어로 DTO를 생성하면서 받기
  
  - `select new DTO(m.a, m.b) from Member m`
  
  - 패키지 명을 포함한 전체 클래스 명을 입력해야 한다.
  
  - 해당 클래스의 생성자를 실제로 호출하기 때문에, 적합한 생성자가 있어야 한다.

---

## 페이징

JPA는 페이징을 단 2개의 API로 추상화한다.

- `setFirstResult(int startPosition)`

- `setMaxResults(int maxResult)`

DBMS 종류마다 페이징 쿼리 형태가 다 다르고, 너무 지저분한 경우 (오라클) 도 있는데 그걸 다 신경쓰지 않을 수 있다.

---

## 조인

내부 조인, 외부 조인, 세타 조인 모두 가능하다.

- 연관관계가 존재하는 엔티티 사이에서 조인을 거는 게 일반적이긴 하지만,

- 연관관계가 없더라도 JPA, Hibernate 버전이 충분히 높다면 외부 조인까지도 가능하다.

---

## 서브 쿼리

스펙 자체는 SQL 기본과 동일하다.

### 한계

JPA 표준 스펙은 WHERE, HAVING 절에서만 서브쿼리 사용이 가능하다.

- 하지만 Hibernate가 SELECT 절에서도 가능하도록 지원한다.

**FROM절** 서브쿼리는 구현체 무관 JPQL에서 사용할 방법이 없다.

- 조인으로 풀어서 해결해야 한다.

---

## 그 외

조건식(CASE), COALESCE, NULLIF 등 사용 가능

CONCAT, SUBSTRING ,COUNT, ABS 등등 사용 가능

- ANSI SQL 에서 지원하는 함수는 대부분 지원한다.

- DBMS 종속적인 dialect 함수도 사용할 수 있긴 하다.

### 사용자 정의 함수 호출

사용하는 DBMS의 dialect 객체를 상속받아서, 직접 함수를 등록하는 방법이 가능하다.

---

## 경로 표현식

`.` 점을 찍어서 객체 그래프를 탐색하는 것

- **상태 필드** : 단순히 값을 저장하는 필드

- **연관 필드** : 연관관계를 위한 필드
  
  - **단일 값 연관 필드**
  
  - **컬렉션 값 연관 필드**

### 특징

- **상태 필드** : 경로 탐색의 **끝** 지점

- **단일 값 연관 경로** : **묵시적 내부 조인**이 발생하여, 추가적인 탐색이 가능하다.
  
  - `m.team.name` 이런 식으로 타고 들어가는 것

- **컬렉션 값 연관 경로** : 마찬가지로 **묵시적 내부 조인**이 발생하지만, 추가적인 탐색은 불가하다.
  
  - `size` 같은 함수 정도 사용이 가능함
  
  - FROM 절에서 **명시적으로 조인**하여 **별칭**을 생성하면 그 때부터 가능

### 명시적 조인, 묵시적 조인

- 명시적 : join 키워드를 직접 사용하는 케이스

- 묵시적 : **경로 표현식에 의해** 묵시적으로 SQL 조인이 발생하는 케이스

실무에서는, **명시적 조인만 사용하도록 한다**.

### 주의사항

가급적 묵시적 조인은 쓰지 말자.

- 조인은 SQL 튜닝에서 너무 중요한 포인트이기 때문에,

- 조인이 묵시적으로 일어나는 상황 자체를 만들지 말자

---

## 페치 조인

SQL 조인의 한 종류가 아니라,

JPQL에서 **성능 최적화**를 위해 제공하는 기능이다.

연관된 엔티티나 컬렉션을 **SQL 한번으로 조회할 때 사용**한다.

- `join fetch` 명령어 사용

### 엔티티 페치 조인

연관관계가 존재하는 엔티티 사이에서 LAZY 로딩 전략으로 인해 쿼리가 지나치게 많이 발생하는 것을 방지함

- fetch 전략 자체를 EAGER로 설정하는 것과 달리

- JPQL 작성 수준에서 EAGER 로딩을 직접 활용하는 느낌

### 페치 조인과 DISTINCT

JPQL의 DISTINCT는 2가지 기능을 제공한다.

- SQL 수준에서의 DISTINCT

- 애플리케이션 수준에서의 엔티티 중복 제거

#### 상세

```java
String query
= "select t from Team t join fetch t.members";
```

위와 같은 페치 조인을 사용할 때 발생하는 쿼리는 아래와 같다.

```sql
select m.*, t.*
from team t
join member m
on m.team_id = t.team_id
```

개발자의 의도는 아마도 **Team** 객체와 그에 부수되는 Members 정보를 조회하는 것이였겠지만,

작성되는 SQL의 구조상 어쩔 수 없이 **중복되는 Team 객체**가 발생할 수 있다.

이 상태에서 SQL 수준의 DISTINCT를 걸어봐야 레코드 값이 완전히 동일한 게 아니기 때문에 원하는 효과를 달성할 수 없다.

바로 이 부분에서, JPQL 은 **애플리케이션 수준에서의 엔티티 중복 제거**를 넣어서 Team 객체 그 자체에 대한 DISTINCT 처리를 진행해준다.

### 페치 조인과 일반 조인의 차이

JPQL 에서 `join` 이라는 키워드를 명시한다는 점에서 혼동의 쇠가 있다.

핵심 차이점은, **일반 조인의 경우 JOIN으로 연관된 엔티티를 함께 조회하지 않는다**.

- 예외적으로, 필드 상에서 로딩 전략을 `EAGER`로 명시했다면 조회할 것

**N+1 문제**는 거의 다 이 방법으로 해결한다고 보면 된다.

### 페치 조인의 특징과 한계

- 페치 조인 대상에는 **별칭**을 줄 수 없다.
  
  - 하이버네이트에서 가능하기는 하지만, 가급적 사용하지 않는다.
  
  - 일단 어색하다. **연관된 엔티티를 불러오기 위해 사용하는 스펙**인데 일부만 필터링한다든지 하는 동작을 넣는 건 위험성이 크다. 정합성 이슈가 우려됨

- 둘 이상의 컬렉션은 페치 조인을 할 수 없다.
  
  - 대충 생각해봐도 쿼리 자체가 구성되기 어렵다. Cartesian이 확정적

- 컬렉션을 페치 조인할 시, **페이징 API**를 사용할 수 없다.
  
  - 페치 조인의 결과물이 어떻게 나올지를 쿼리 작성 시점에 알 수 없기 때문.
  
  - 굳이 페이징 처리가 필요하다면 쿼리를 실행시킨 이후에 메모리에서 프로세싱하는 방법밖에 없다.
  
  - 하이버네이트의 경우 WARN 로그를 남기면서 실제로 **메모리에서 페이징**해준다.
  
  - 당연히 극히 위험한 방법.

#### BatchSize

연관관계에 있는 컬렉션에 대해 lazy loading이 발생할 때, N+1 문제가 발생하는 것을 방지하기 위한 설정

- `@OneToMany` 가 달린 필드에서 `@BatchSize` 를 100으로 명시하면,

- `where id in ( ... )` 이런 식으로 lazy loading 대상 객체를 한꺼번에 100개씩 가져와서 영속성 컨텍스트에 적재해준다.

보통 이 설정은 글로벌로 적용하여 방어 수단으로 활용하고 있다.

`<property name="hibernate.default_batch_fetch_size" value="100"/>`

### 정리

실무에서 글로벌 로딩 전략 (엔티티에 적시된 것) 은 **모두 지연 로딩**으로 통일하고,

**최적화가 필요한 곳에서만 페치 조인을 적용**한다.

- 보통 N+1 문제로 인해 최적화가 필요해진다.

- 페치 조인이 명시되면 글로벌 로딩 전략을 무시한다.

모든 것을 페치 조인으로 해결할 수는 없으나, **객체 그래프를 유지할 때 사용**하면 매우 효과적이다.

여러 테이블을 조인한 결과물이 엔티티가 가진 모양에서 아주 많이 벗어난다면, 차라리 일반 조인을 사용하고 별도의 DTO로 조합하는 게 적합하다.

---

## 다형성 쿼리

조회 대상을 특정 타입의 자식으로 한정할 수 있다.

- `type(i)` 

심지어 캐스팅 처리도 가능하다.

- `treat(i as A)`

---

## 엔티티 직접 사용

JPQL에서 엔티티를 직접 사용하면, SQL에서는 해당 엔티티의 PK값을 알아서 사용해준다.

심지어 바인딩 변수에서도 엔티티를 직접 입력하고 비교연산할 수 있다.

---

## Named 쿼리

쿼리를 미리 선언해두고 이름을 부여하여 재사용하는 JPQL.

**정적** 쿼리만 사용이 가능하다.

애노테이션이나 XML에 정의할 수 있고,

**애플리케이션 로딩 시점에 초기화된다.**

- **로딩 시점에 해당 쿼리를 검증할 수 있다**.

String 형태로 작성한 쿼리임에도 불구하고 부팅 시점에 검증이 된다는 건 큰 장점.

---

## 벌크 연산

JPA는 기본적으로 **변경 감지 기능**을 사용해서 DML 처리를 하고 있는데, 일괄적으로 대량의 데이터를 DML 처리하는 경우 문제가 발생한다.

JPQL을 이용해서 **쿼리 한 번으로 여러 개의 로우를 변경**하는 요구사항을 쉽게 구현할 수 있다.

- `executeUpdate()` : 반환값은 affected rows

- update, delete를 지원함

하이버네이트를 이용하면 `insert into ... select` 연산을 이용한 벌크 연산도 가능하다.

### 주의사항

기본적으로 **영속성 컨텍스트를 무시하고 DB에 직접 쿼리를 날리는 작업**이다.

- 최대한 **벌크 연산을 다른 것보다 우선하여 실행**한다.

- 벌크 연산을 중간에 수행해야 한다면, **영속성 컨텍스트를 초기화**한다.


