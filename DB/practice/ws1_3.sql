#1. world db 사용
use world;

#2. 테이블 구조 파악
select *
from city;

select *
from country;

select *
from countrylanguage;

#3. country table에서 code가 KOR인 자료를 조회
select *
from country
where code = "KOR";

#4. country 에서 gnp-gnpold 양수인 국가 gnp변동량 오름차순정렬
select code, name, gnp, gnpold, (gnp-gnpold) as "gnp 변동량"
from country
where (gnp-gnpold) > 0 
order by (gnp-gnpold);

#5. country에서 continent 중복없이 조회, continent 길이를 정렬
select distinct continent
from country
order by length(continent);

#6. country에서 asia에 속하는 국가들 정보 출력, name 정렬 : TODO
select name, region, population
from country
where continent = "asia"
order by name;

#7. country 독립년도 기록 없고 인구 1만 이상 국가 정보 인구 오름차순 출력
select name, continent, gnp, population
from country
where indepyear is null
and population >= 10000
order by population;

#8. country 인구가 1억~2억, 인구기준 내림차순 상위3개
select code, name, population
from country
where 100000000 <= population and population <= 200000000
order by population desc
limit 3;

#9. country 독립년 800 1810 1811 1901 독립년기준 오름차순
select code, name, indepyear
from country
where indepyear in (800,1810,1811,1901)
order by indepyear, code desc;

#10. country에 region에 asia, name 두번째글자 o 정보
select code, name, region
from country
where region like "%asia%"
and name like "_o%";

#11. 홍길동과 hong의 글자길이 출력
select char_length("홍길동"), char_length("hong")
from dual;

#12. country 에 governmentform에 republic, name 10 이상, name 길이 내림차순, 상위 10개
select code, name, governmentform
from country
where governmentform like("%republic%")
and char_length(name)>=10
order by char_length(name) desc
limit 10;

#13. country에서 code가 모음으로 시작, name 오름차순, 3번부터 3개
select code, name
from country
where code rlike "^[aeiouAEIOU]"
order by name
limit 2,3;

#14. country에서 name을 맨 앞 맨 뒤 2글자 제외 나머지 * 처리
select name,
concat(
substring(name, 0, 2),
insert(name, 3, char_length(name)-2, repeat("*", char_length(name)-4)),
substring(name, char_length(name)-1, 2)
) as guess
from country;

#15. countruy에서 region을 중복없이, 공백은 _로, region 길이로 정렬
select distinct
replace(region, " ", "_")
as "지역들"
from country
order by char_length(region) desc;

#16. country에서 인구 1억 이상 국가 1인당 점유면적 반올림 소수점 3자리, 오름차순
select name, surfacearea, population, 
round((surfacearea/population),3) as "1인당 점유면적"
from country
where population >= 100000000
order by 4;

#17. 현재 날짜, 올해 며칠 지났는지, 100일 후 몇 일인지
select curdate() as "오늘",
dayofyear(curdate()),
date_add(curdate(), interval 100 day)
from dual;

#18. country에서 asia에 있는 나라, 희망 수명 있는 경우, 기대 수명 정렬
select name, continent, lifeexpectancy,
	case when lifeexpectancy > 80 then "장수국가"
    when lifeexpectancy > 60 then "일반국가"
    else "단명국가"
    end "구분"
from country
where continent = "asia"
order by lifeexpectancy;

#19. country에서 gnp 향상, 신규 표시, name 정렬
select name, gnp, gnpold, 
	case when gnpold is null then "신규"
    else (gnp-gnpold)
    end "gnp 향상"
from country
order by name;

#20. 2020년 어린이날 평일이면 행복, 주말이면 불행
select weekday('2020-05-05'),
	case when weekday('2020-05-05') > 0 and weekday('2020-05-05') < 6 then "행복"
    else "불행"
    end "행복여부"
from dual;
