create table member(
	member_no number primary key
	,member_id varchar(20) not null unique
	,member_passwd varchar(30) not null
	,member_name varchar(20) not null
);

CREATE TABLE diary(
	diary_no NUMBER PRIMARY KEY
	, member_no NUMBER
	, member_id varchar(20)
	, regist_date varchar(20)
	, regist_time varchar(20)
	, weathertype NUMBER
	, feeltype NUMBER
	, image varchar(50)
	, content varchar(500)
	, FOREIGN key(member_no) REFERENCES member(member_no)
	, FOREIGN key(member_id) REFERENCES member(member_id)
	, FOREIGN key(weathertype) REFERENCES weathertype(weather_id)
	, FOREIGN key(feeltype) REFERENCES weathertype(weather_id)
);

CREATE TABLE weathertype(
	weather_id number PRIMARY KEY
	, weathertype varchar(20)
);

INSERT INTO weathertype(weather_id, weathertype) values(1, '맑음');
INSERT INTO weathertype(weather_id, weathertype) values(2, '흐림');
INSERT INTO weathertype(weather_id, weathertype) values(3, '비 조금');
INSERT INTO weathertype(weather_id, weathertype) values(4, '소나기');
INSERT INTO weathertype(weather_id, weathertype) values(5, '번개');

create sequence seq_member increment by 1 start with 1;
create sequence seq_diary increment by 1 start with 1;
create sequence seq_weathertype increment by 1 start with 1;

INSERT INTO diary(diary_no, member_no, member_id, regist_date, regist_time, weathertype, feeltype, image, content)
 values(seq_diaryno.nextval, ?, ?, ?, ?, ?, ?, ?);