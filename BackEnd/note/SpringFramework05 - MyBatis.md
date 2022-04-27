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

**그 외 내용은 기술문서를 적극 참조할 것**

---

# MyBatis - Spring

## 주요 Component

### SqlMapConfig

xml로 만들거나 java class로 만들 수 있음

데이터베이스 접속 주소 정보나 객체 alias, Mapping 파일의 경로 등 **고정된 환경 정보**를 설정.

따로 만들어 둔 dbinfo.properties와 mapper.xml들을 참조하게 됨

### dbinfo.properties

SqlMapConfig가 읽는 단순 파일

DB에 대한 정보를 저장

### mapper.xml

종전 DAO 클래스가 가지고 있던 메서드와 sql문을 그대로 재현하고 있는 xml.

여러 태그들이 지원된다.

- namespace, insert/update/delete/select 등

- 여기에 id, parameterType, resultType, resultMap 등을 입력

### SqlSessionFactory

SqlSession을 생성함. Builder를 사용할 필요

MyBatis 설정 파일을 바탕으로 생성된다.

- mapper 정보를 모두 읽은 sqlMapConfig.

### SqlSession

SQL 실행과 Transaction 관리를 실행하는 핵심 class

이후부터 DAO는 sqlSession에 메서드를 입력하게 된다.

- mapper.xml에 명시한 태그 및 id를 지목해서 작동시킴

## 직접 하다가 찾은 것들

- `SqlMapConfig` 클래스를 따로 만들었을 때 component:scan이 닿기 위해 `@Component` 달아줘야 함

- DTO와 table의 column은 일치할 필요가 없음. 객체 간 연동만 잘 구현하면 됨

- 두 개 이상의 table을 참조할 시, resultMap을 잘 사용하는 것이 필수.
  
  - `<association propery>` 로 resultMap의 type이 되는 객체에 그 property에 해당하는 다른 객체를 주입할 수 있다.

---

# Mapper Interface

## MyBatis3의 Mapper Interface

mapping 파일에 기재된 SQL을 호출하기 위한 interface.

SQL을 호출하는 프로그램을 Type safe하게 기술하기 위해 등장.

---

# MyBatis - Spring 연동

## 개요

MyBatis를 Standalone 형태로 사용하는 경우, `SqlSessionFactory` 객체를 직접 사용하게 됨.

하지만 스프링을 사용하는 경우

- 스프링 컨테이너에 MyBatis 관련 bean을 등록하여 MyBatis를 사용한다.

- 스프링에서 제공하는 트랜잭션 기능을 사용할 수 있다.

MyBatis를 스프링과 연동하기 위해서는, 라이브러리가 필요하다.

- pom.xml에 의존성을 추가함.

## DataSource 설정

스프링에서 데이터 소스를 관리하게 되므로, MyBatis 설정파일에서 DS관련 설정이 생략될 수 있다.

대신 application-context.mxl에 데이터소스를 설정함.

- 데이터소스는, dataSource 아이디를 가지는 bean으로 데이터베이스 연결정보를 가진 객체.

MyBatis와 스프링을 연동하면, 데이터벵스 설정과 트랜잭션 처리가 스프링에서 관리된다.

## 트랜잭션 관리자 설정

`transactionManager` 라는 id를 가지는 bean. 트랜잭션을 관리하는 객체

MyBatis는 JDBC를 그대로 사용하기 때문에, `DataSourceTransactionManager` 타입의 빈을 사용하게 된다.

어노테이션 기반으로 트랜잭션을 관리하기 위해, `<tx:annotation driven>`을 context-root.xml에 설정하고

비로소 스프링은 메서드나 클래스에 `@Transcational`이 선언되어 있으면 AOP를 통해 트랜잭션을 처리할 수 있다.

## SqlSessionFactoryBean 설정

MyBatis 애플리케이션은 `SqlSessionFactory`를 중심으로 수행된다.

스프링에서 해당 객체를 생성하기 위해, root-context.xml에 빈으로 등록한다.

- 이 때 사용할 데이터소스와 mybatis 설정파일에 대한 정보를 property로 입력해야 한다.

## Mapper 빈 등록

Mapper 인터페이스를 사용하기 위해, 스캐너를 사용해 자동 등록하거나 직접 빈으로 등록한다.

- `mapperScannerConfigurer`를 설정하면, Mapper 인터페이스를 자동으로 검색하여 빈으로 등록한다.

- `MapperFactoryBean`클래스는 매퍼 인터페이스를 직접 등록할 때 사용한다.

## MyBatis Configuration 파일

스프링 사용 시, DB 접속정보나 Mapper 관련 설정이 스프링 빈으로 등록되기 때문에 configuration 파일이 간소화된다.

따라서, 해당 파일에는 `typeAlias`, `typeHandler` 같이 스프링에서 직접 관리하지 않는 정보만 설정한다.

## 데이터 접근 객체 구현

특정한 기술을 사용해, 데이터 저장소에 접근하는 방식을 구현한 객체.

`@Repository`로 데이터 접근 객체를 설정하고

`@Autowired`로 사용하려는 Mapper 인터페이스를 데이터접근 객체(`ServiceImpl`)와 의존관계를 설정한다.

## 직접 하다가 찾은 것들

- RepoImpl은 사라지게 된다. Mapper로 이름도 바꿀 수 있다

- ServiceImpl에서 메서드를 호출하게 되면서 종전보다 Service 객체가 복잡해진다.

- root-context.xml 설정이 중요하다
  
  - sqlSessionFactoryBean 설정하면서 dataSource와 configLocation, mapperLocations 설정하기
  
  - mybatis-spring;scan 하기

- 정리하자면,
  
  - 원래 Repo 구현체에서 접근하던 SqlSession을 Spring bean으로 생성해서 관리
  
  - Repo 구현체에서 입력하던 코드는 충분히 Service에서 입력하는 것으로 대체 가능하므로 대체
  
  - mapper.xml들만 제대로 만들어놨다면 나머지는 MyBatis가 잘 처리해줌.
