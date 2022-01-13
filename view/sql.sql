1. 회원가입한 정보를 저장할 수 있는 'web_member'테이블을 만드시오.
   컬럼명 : m_email / m_pw / m_tel / m_address

create table web_member
(m_email varchar(50),
m_pw varchar(50) not null,
m_tel varchar(50) not null,
m_address varchar(50) not null,
constraint web_member_pk primary key(m_email)
)

select * from web_member


create table web_message
(m_num number,
m_sendName varchar(50) not null,
m_receiveEmail varchar(50) not null,
m_content varchar(200) not null,
m_sendDate date not null,
constraint web_message_pk primary key(m_num)
)

select * from web_message

create sequence num_seq
increment by 1
start with 1


insert into web_message values(num_seq.nextval,'test','1111@1111','테스트내용',sysdate)






