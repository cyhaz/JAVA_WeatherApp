create table member(
	member_no number primary key
	,member_id varchar(20) not null unique
	,member_passwd varchar(30) not null
	,member_name varchar(20) not null
);

CREATE TABLE diary(
	diary_no NUMBER PRIMARY KEY
	, member_no NUMBER
	, regist_date varchar(20)
	, regist_time varchar(20)
	, weathertype NUMBER
	, feeltype NUMBER
	, image varchar(50)
	, content varchar(500)
	, FOREIGN key(member_no) REFERENCES member(member_no)
	, FOREIGN key(weathertype) REFERENCES weathertype(weather_id)
	, FOREIGN key(feeltype) REFERENCES weathertype(weather_id)
);

CREATE TABLE weathertype(
	weather_id number PRIMARY KEY
	, weathertype varchar(20)
);

CREATE TABLE feeltype(
	feel_id NUMBER PRIMARY KEY
	, feeltype varchar(20)
);

INSERT INTO weathertype(weather_id, weathertype) values(1, '맑음');
INSERT INTO weathertype(weather_id, weathertype) values(2, '흐림');
INSERT INTO weathertype(weather_id, weathertype) values(3, '비 조금');
INSERT INTO weathertype(weather_id, weathertype) values(4, '소나기');
INSERT INTO weathertype(weather_id, weathertype) values(5, '번개');

INSERT INTO feeltype(feel_id, feeltype) values(1, '맑음');
INSERT INTO feeltype(feel_id, feeltype) values(2, '흐림');
INSERT INTO feeltype(feel_id, feeltype) values(3, '비 조금');
INSERT INTO feeltype(feel_id, feeltype) values(4, '소나기');
INSERT INTO feeltype(feel_id, feeltype) values(5, '번개');

create table todolist(
	todolist_no number primary key
	, member_no number
	, status_no number
	, content varchar(100) not null
	, duedate varchar(10)
	, foreign key(member_no) references member(member_no)
	, foreign key(status_no) references status(status_no)
);

create table status(
	status_no number primary key
	, status varchar(10)
);

insert into status(status_no, status) values(1,'할 일');
insert into status(status_no, status) values(2,'진행 중');
insert into status(status_no, status) values(3,'완료');

create sequence seq_member increment by 1 start with 1;
create sequence seq_diary increment by 1 start with 1;
create sequence seq_todolist increment by 1 start with 1;

INSERT INTO member(member_no, member_id, member_passwd, member_name)
 values(seq_member.nextval, 'cy', 'aaaa', '박초연');