# MySQL

## RDBMS & SQL

### RDBMS?

- **관계형** 데이터베이스 시스템

- **테이블 기반**의 DMBS.
  
  - 데이터를 테이블 단위로 관리.
    
    - 하나의 테이블은 여러 개의 컬럼으로 구성
  
  - 중복 데이터를 최소화시킴
    
    - 같은 데이터가 여러 컬럼 또는 테이블에 존재할 경우, 데이터 수정 시 문제 발생 가능성이 높아짐. -> **정규화** 필요
  
  - 여러 테이블에 분산되어 있는 데이터를 검색 시 테이블 간의 관계(join)를 이용하여 필요한 데이터를 검색.

### SQL (Structured Query Language)

#### SQL

- DB에 있는 정보를 사용할 수 있도록 지원하는 언어

- 모든 DBMS에서 사용 가능

- 대소문자는 구별하지 않음. (단, 데이터의 대소문자는 구분)

#### SQL 구문

- DCL, DDL, DML로 구분하며, 여러 종류가 있다.

- insert, update, delete, select, create, alter, drop, rename, commit, rollback, grant, revoke 까지

### Data Type

#### 문자형

- CHAR: 고정 길이를 갖는 문자열

- VARCHAR: 가변 길이를 갖는 문자열

- TEXT: 최대 65535 byte

- 그 외 tinytext, mediumtext, longtext, enum(열거형), set(집합형)

#### 숫자형

#### 날짜형

#### 이진 데이터 타입

### table 생성

## DML (Insert, Update, Delete)

### Insert

### Update

### Delete

## DML(Select)

### Diagram

### 기본 Select

### alias, 사칙연산, NULL

### CASE, when, END

### Where

`Select, from, where` 가 주어질 때 실행 순서는?

- from -> where -> select

### And, or, not

### In

### Between

### Null 비교

### Like

### Order by

`Select, from, where, order by`가 주어질 때 실행 순서는?

- from -> where -> select -> order by

- 여러 기준으로 정렬 가능: 순차적으로 이루어짐

## 내장함수

### MySQL 내장함수

- 숫자 관련, 문자 관련, 날짜 관련, 논리 관련, 그룹 함수



## Transaction

- 데이터베이스의 상태를 변화시키는 일종의 작업 단위

- 여러 도구들이 존재
  
  - commit, rollback



## Aggregation

## Group by Clause

### group by 절

- select 문에서 group by 절을 사용할 경우 DB는 쿼리된 테이블의 행을 **그룹으로 묶는다.**

- DB는 선택 목록의 집계 함수를 각 행 그룹에 적용하고 각 그룹에 대해 단일 결과 행을 반환한다.

- group by 절을 생략하면 DB는 선택목록의 집계 함수를 쿼리된 테이블의 모든 행에 적용한다.

- select 절의 모든 요소는 group by 절의 표현식, 집계 함수를 포함하는 표현식 또는 상수만 가능

- 형식
  
  ```sql
  select columns
  from table_name
  where conditions
  group by grouping columns
  having grouping conditions
  order by col [ASC | DESC]
  ```
  
  **실행순서**는 512346. 꼭 외우기!



### having 절

- group by한 결과에 조건을 추가

- 쿼리의 실행 순서를 보면 where절이 group by절보다 먼저 실행되기 때문에 **aggregate 조건은 having절에 작성.**
  
  - where는 일반조건, having은 그룹조건.

- order  by는 가장 마지막에 실행되므로, 그룹함수가 사용된 상황에서도 별 문제없이 넣을 수 있다.



## Set Operator

### Set (집합연산자)

- 모든 집합 연산자는 동일한 우선순위를 갖는다.

- select 절에 있는 column의 개수와 type이 일치해야 한다.
