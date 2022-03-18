use ssafy_user;

select * from user;
select * from board;

#1.	user 테이블에 전채 몇개의 행이 있는지 조회하시오.
select count(*) as '사용자 수'
from user;

#2.	전체 user의 age 평균을 조회하시오.
select AVG(age) as '평균' 
from user;

#3.	전체 사용자의 age 합을 조회하시오.
select SUM(age) as '총 합' 
from user;

#4.	age가 35 이상인 사용자의 age 평균을 둘째짜리 까지 반올림 하여 조회하시오.
select round(avg(age),2) as '평균' 
from user 
where age >= 35;

#5.	writername로 그룹핑 했을때 각 사용자가 몇 개의 board를 가지고 있는지 조회하시오.
select writername, count(*)as '개수' 
from board 
group by writername;

#6.	board 를 writername로 그룹핑 했을때 각 이름이 ‘토르’ 인 사용자의 의 제일 최근 create time을 조회하시오.
select writername, max(createtime) as '날짜' 
from board
group by writername
having writername = '토르';

#7.	board 를 writername로 그룹핑 했을 때 2017년 이후 각 사용자가 몇 개의 board를 가지고 있는지 조회하시오.
select writername, createtime, count(*) as '개수' 
from board 
group by writername 
having createtime > '2017-01-01 00:00:00'


