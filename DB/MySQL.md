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

#### table 생성

- CHAR: 고정 길이를 갖는 문자열

- VARCHAR: 가변 길이를 갖는 문자열
