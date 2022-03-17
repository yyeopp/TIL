create database ssafy_user;
use ssafy_user;

drop table if exists user;
drop table if exists board;

CREATE TABLE `user` (
	`id` varchar(40) NOT NULL PRIMARY KEY,
    `password` varchar(40) NOT NULL,
    `name` varchar(40) NOT NULL,
    `email` varchar(40) NOT NULL,
    `age` int NOT NULL
)ENGINE=InnoDB;

CREATE TABLE `board` (
	`num` int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `createtime` datetime,
    `title` varchar(40) NOT NULL,
    `content` varchar(100) NOT NULL,
    `writername` varchar(40) NOT NULL
)ENGINE=InnoDB;



INSERT INTO `user` (id, password, name, email, age) 
VALUES
('BlackWidow', '1278','나타샤 로마노프', 'blackwidow@ssafy.com', 38),
('CaptainAmerica', '5678','스티브 로저스', 'Captain@ssafy.com', 30),
('Hulk', '2486','브루스 배너', 'hulk@ssafy.com', 54),
('Ironman', '1234','토니 스타크', 'ironman@ssafy.com', 53),
('Thor', '1111','토르', 'GodOfThunder@ssafy.com', 1500),
('Ultron', '2355','울트론', 'ultron@ssafy.com', 0);


INSERT INTO `board` (num, createtime, title, content, writername)
VALUES 
(0, '2015-04-23 12:00:00','지구의 적은 인류다', '어벤져스만 아니었다면...' ,'울트론'),
(0, '2015-04-23 13:00:00','울트론님 왜 제말을 안들으세요', '아빠말을 잘 안듣는 어린이네요', '토니 스타크'),
(0, '2017-10-25 17:00:00','헐크님 정신이 드시나요?', '살살 하걸 그랬네요..','토르'),
(0, '2017-10-25 17:10:10','헐크님 말좀 해보세요','아직도 누워계신가요?', '토르'),
(0, '2017-10-25 17:15:20','헐크 그만괴롭히세요 토르님','왜 자꾸 우리 헐크 기를 죽이고 그래요', '나타샤 로마노프'),
(0, '2019-04-29 09:10:00','묠니르 가벼군요','깃털처럼 가벼워요', '스티브 로저스'),
(0, '2019-04-24 09:10:00','헐크버스터 최고','너무 재밌네요', '브루스 배너');

commit;

use ssafy_user;

-- 여기부터 과제 풀이
#1. user 테이블 내의 전체 데이터를 조회하시오.
select *
from user;

#2. user 테이블 내의 모든 name 을 조회하시오.
select name
from user;

#3.	board 테이블 내의 전체 data를 createtime이 빠른 것부터 (오름차순) 조회하시오.
select * 
from board
order by createtime;

#4.	name이 '울트론' 인 사용자 정보를 조회하시오.
select * 
from user 
where name='울트론';

#5.	name이 '토' 로 시작하는 사용자 정보를 조회하시오.
select * 
from user;


#6.	title에 '헐크' 가 포함되는 board 정보를 조회하시오.
select * 
from board
where title like '%헐크%';


#7.	id가 ‘Ironman’ 또는 ‘CaptainAmerica’ 인 사용자의 정보를 조회하시오.
select * 
from user
where id='Ironman' or id='CaptainAmerica';

#8.	age 가 50 이상인 user 중에서 name 에 ‘토’ 라는 단어가 포함된 사용자 정보를 조회하시오.
select * 
from user
where age >= 50
and name like "%토%";

#9.	board 에서 createtime을 내림차순 정렬해서 상위 3개만 출력하시오.
select *
from board
order by createtime desc
limit 3;


#10. board 에서 createtime의 년도 기준으로 내림차순 정렬하여 출력하시오. 작성된 년도가 같다면 num을 기준으로 오름차순 한다.
select * 
from board
order by YEAR(createtime) desc, num;


#11. user 에서 id의 세번째 글자가 ‘o’ 인 data를 출력하시오.
select * 
from user 
where id like "__o%";

#12. board 에서 content의 공백을 모두 ‘+’ 로 치환해서 출력하시오.
select replace(content, " ", "+")
from board; 

#13. board 에서 writer를 중복없이 출력하시오.
select DISTINCT writername 
from board;