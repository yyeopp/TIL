# DML - 단일행 내장 함수

---

# SQL 내장 함수

DBMS에서 프로그래머의 편의를 위해 제공하는 함수.

별도의 코딩 없이 복잡한 기능을 제공.

벤더 별로 차이가 상당하다. 리턴 자료형이나 함수 이름 등..

- 표준이 존재하고 상당 부분을 커버하고 있지만,
- 벤더 고유의 내장 함수 또한 아주 많이 있다. 웬만하면 안 쓰는 방향으로 할 필요
- 오늘 다룰 내용들은 **모두 표준**

### 단일행 내장 함수

집계 함수와 달리, 한 행에 대해서 적용되는 내장 함수

SELECT, WHERE, ORDER BY 절에서도 쓰는 게 가능하다.

문자형, 숫자형, 날짜형, 변환형, NULL 관련, 정규식, 논리 제어 함수

### 다중행 내장 함수

입력이 여러 개의 행인 내장 함수

집계 함수, 윈도우 함수, 그룹 함수

---

# 문자형 함수

```sql
ASCII(char), CHAR(integer)
LOWER(str), UPPER(str)
LENGTH(str)
CONCAT(str1, str2, ...)
REPEAT(str, n)
REVERSE(str)
STRCMP(str1, str2)
# 두 문자열을 비교하여 str1이 str2보다 작으면 -1, 같으면 0, 크면 1
TRIM(str)
TRIM({BOTH|LEADING}TRAILING} removed_str FROM str)
# str에서 맨 앞/뒤의 removerd_str을 제거함
INSTR(str, substr)
# str의 앞에서부터 부분문자열 substr이 처음 나타날 때 그 시작 위치를 리턴. 1부터 시작
SUBSTR(str, position[ , length])
# str의 position부터 length만큼 문자열을 잘라서 리턴. length 생략 시 마지막까지
REPLACE(str, from_str, to_str)
# str에 있는 모든 from_str을 to_str으로 대체함
```

`instr`과 `substr`, `replace` 3가지가 아주 중요하다.

셋을 중첩 사용하여 문자열을 프로세싱하는 방법을 자주 사용하기 때문

- host language에서 처리하는 경우가 많은데, SQL에서 더 빠르고 정확하게 처리 가능한 부분

---

# 숫자형 함수

```sql
SIGN(x)
ABS(x)
FLOOR(x)
CEILING(x)
ROUND(x,d)
TRUNCATE(x,d)
MOD(x,y)
POWER(x,y)
LOG(b,x)
EXP(x)
SQRT(x)
SIN(x), COS(x), TAN(x)
ASIN(x), ACOS(x), ATAN(x)
```

---

# 날짜형 함수

날짜와 시간을 다루는 게 DB에서 가장 어려운 부분 중 하나

## 날짜의 표현

숫자형으로는,

`YYYYMMDDHHMMSS.uuuuuu` : 20자리 **실수**

문자형으로는,

`YYYY-MM-DD HH:MM:SS.uuuuuu` : 26자리 **문자열**

내부적으로는 **숫자형**으로 저장된다.

- 공간을 절약하고, 산술 연산이 간편함

출력 시의 디폴트는 **문자형이다.**

- **컨텍스트**에 따라 숫자형으로 출력되기도 한다.

### 날짜형 데이터 타입

YEAR, DATE, TIME, DATETIME, TIMESTAMP

- DATETIME이랑 TIMESTAMP은 정보 내용에서 차이점이 없으나, 내부적으로 저장되는 자료형이 다르다.
- DATETIME은 위에서 보다시피 실수형인데,
- TIMESTAMP는 유닉스 시간이 적용된 카운터이므로 정수형.
  - DATETIME 대비 저장용량을 세이브하는 효과가 있다. 그래서 권장된 적도 있음
  - 근데 디스크 용량을 아끼는 게 별로 중요해지지 않아서, 요즘은 신경 안 쓴다.

## 주요 함수

```sql
SYSDATE(), NOW()
DATE(), TIME(), TIMESTAMP()
YEAR(), QUARTER(), MONTH(), MONTHNAME()
DAY(), DAYNAME(), WEEKDAY()
TIME(), HOUR(), MINUTE(), SECOND()
TIMESTAMPDIFF()
DATE_FORMAT()
GET_FORMAT()
STR_TO_DATE()
```

### 현재 시각 관련 함수 NOW(), SYSDATE()

공통점은, 컨텍스트에 따라 문자형, 숫자형(정수 or 실수)으로 출력된다는 점.

차이점은,

- sysdate()의 경우 **해당 함수가 호출된 시점의 시스템 시각**을 찍는다.
- now()의 경우 **해당 명령어가 실행된 시점의 시각**을 찍는다.
- 인위적으로 `sleep()`을 줘가면서 차이점을 확인해볼 수 있는데, 업무 중에는 딱히 신경쓸 부분이 아니라서 대부분 구분하지 않는다고
- 보통 더 짧고 직관적인 now()를 쓴다.

NOW()는 일반적으로 날짜형 컬럼의 **디폴트 값 할당**에 사용된다.

- `created_time DATETIME NOT NULL DEFAULT NOW()`

### Datetime Dimension 관련 함수

다양한 함수가 제공되고, 리턴하는 데이터와 그 타입이 천차만별이므로 정확히 구분해서 사용해야 한다.

### 날짜 연산 시 주의사항

날짜형(date, time, datetime) 컬럼을 직접 더하거나 빼면 안 된다.

산술연산을 적용할 시, **컨텍스트**에 의해 숫자형으로 인식하면서 시간 계산이 아닌 단순 숫자 간의 계산으로 출력된다.

대신 시간 계산 시,

- `TIMESTAMPDIFF()` 함수나 `INTERVAL` 표현식을 사용한다.
- 웬만하면 표준인 `TIMESTAMPDIFF()`을 애용하자

## INTERVAL 표현식

날짜형 컬럼의 임의의 날짜, 시간 값을 더하거나 뺄 수 있다.

`INTERVAL value <unit>`

- unit으로는 생각할 수 있는 모든 단위들이 가능하다.

### 예제: 달의 첫 날과 마지막 날, 날짜수

```sql
SELECT DATE(NOW()) - INTERVAL (DAY(NOW ()) - 1) DAY AS firstDayOfMonth
       DATE(NOW()) + INTERVAL 1 MONTH 
                   - INTERVAL DAY(NOW() + INTERVAL 1 MONTH) DAY AS lastDayOfMonth
       DAY(DATE(NOW()) + INTERVAL 1 MONTH 
                   - INTERVAL DAY(NOW() + INTERVAL 1 MONTH) DAY) AS noOfDaysInMonth
```

별 것 아닌 문제 같은데 막상 짜보려면 아주 골치아픈 코드

## TIMESTAMPDIFF() 함수

`TIMESTAMPDIFF(unit, begin, end)`

datetime 단위 두 개를 넣으면, 그 차이를 *주어진 단위*로 계산해준다.

- 자동으로 *만* 의 개념이 적용된다.

## DATE_FORMAT() 함수

날짜와 시간의 출력 형식을 지정하여, **문자열**로 출력한다.

`DATE_FORMAT(datetime, '<format>');`

- format에는 Y, y, M, m, D, d, H, h, i, S, s, p를 사용한다.
  - Y는 4자리, y는 2자리, M은 글자, m은 숫자..
  - 분 단위가 i를 쓴다는 점 정도 주의
  - format specifier끼리 붙여쓰면 에러가 난다. 뭐가 됐든 넣자
- 출력값이 **문자형** 타입이라는 점을 주의하자.
  - `DATE()` 함수 같이 **날짜형** 타입을 리턴하는 함수와 구분해야 한다.
  - 겉으로 같게 보여도 자료형이 상이하다.

## STR_TO_DATE() 함수

입력된 **문자열**을 주어진 포맷으로 해석하여, **날짜형** 타입으로 변환한다.

`STR_TO_DATE(string, '<format>');`

## MySQL에서의 시간대 처리

클라우드 상의 DBMS가 시간을 저장할 때, 저장된 시간은 클라이언트 측이 아닌 클라우드 상 서버의 시간이다.

사용자 출력 시에는 서버 시간을 클라이언트 측 시간으로 변환할 필요가 있다.

MySQL의 경우 `CONVERT_TZ()` 함수를 제공한다.

---

# 타입 변환 함수

특정 데이터 타입을 다른 데이터 타입으로 변환하는 경우가 잦아서 함수로 제공된다.

`CAST(expr AS datatype);`

`CONVERT(expr, datatype);`
