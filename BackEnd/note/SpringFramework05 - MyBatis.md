# SpringFramework - MyBatis

---

# MyBatis

## 개요

Java Object와 SQL문 사이의 자동 Mapping 기능을 지원하는 ORM Framework.

SQL을 별도의 파일로 분리해서 관리하고,

Object - SQL 사이의 parameter mapping 작업을 자동으로 해준다.

JPA처럼 새로운 DB 프로그래밍 패러다임을 익혀야 하는 부담이 없이, 개발자가 익숙한 SQL을 그대로 이용하면서도 JDBC 코드 작성의 불편함을 제거해주고, 도메인 객체나 VO 객체를 중심으로 개발이 가능하다.

## 특징

쉬운 접근성과 코드의 간결함

- XML 형태로 서술된 JDBC 코드라고 봐도 됨. JDBC의 기능을 대부분 제공

- 수동적인 parameter 설정과 query 결과에 대한 mapping 구문을 제거.
  
  - 수동적 설정: ?에다가 setString하고, getParameter하고, dto에 set하고 ...

SQL문과 프로그래밍 코드의 분리

- SQL 작성과 관리 또는 검토를 DBA에게 맡길 수 있게 됨

- SQL 변경이 있을 때마다 자바 코드를 수정하거나 컴파일하지 않아도 됨

다양한 프로그래밍 언어로 구현가능


