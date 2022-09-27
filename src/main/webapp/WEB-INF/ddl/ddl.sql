-------------------------------------------------------------------------------------
-- root account
------------------------------------------------------------------------------------- 

-- database 생성
CREATE DATABASE board;

-- 사용자 생성
create user '계정명'@'localhost' identified by '계정비밀번호' ;
create user '계정명'@'%' identified by '계정비밀번호' ;

-- 사용자에 권한 부여
grant all privileges on 'board'.* to '계정명'@'localhost' with grant option;
grant all privileges on 'board'.* to '계정명'@'%' with grant option;
flush privileges;

-- 함수 생성 권한 부여
set global log_bin_trust_function_creators=1;


-------------------------------------------------------------------------------------
-- user account
------------------------------------------------------------------------------------- 

-- board 데이터베이스 사용
USE board;

-- board 테이블 생성
CREATE TABLE 'board' (
  'bid' int NOT NULL AUTO_INCREMENT COMMENT '게시물아이디(PK)',
  'btitle' varchar(400) NOT NULL COMMENT '게시물제목',
  'bcontent' text COMMENT '게시물내용',
  'bwdate' timestamp NULL DEFAULT NULL COMMENT '게시물작성일시',
  'bdomain' varchar(40) DEFAULT NULL COMMENT '게시물분류',
  'bwriterid' varchar(20) NOT NULL COMMENT '작성자아이디',
  PRIMARY KEY ('bid')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- boardfile 테이블 생성
CREATE TABLE 'boardfile' (
  'bfid' int NOT NULL AUTO_INCREMENT COMMENT '파일아이디(PK)',
  'bfcfn' varchar(400) NOT NULL COMMENT '클라이언트파일명',
  'bfsfn' varchar(400) NOT NULL COMMENT '서버파일명',
  'bfsize' int NOT NULL COMMENT '파일사이즈',
  'bfbid' int NOT NULL COMMENT '게시물아이디(FK)',
  'bfcontenttype' varchar(30) DEFAULT NULL,
  PRIMARY KEY ('bfid'),
  KEY 'bfbid' ('bfbid'),
  CONSTRAINT 'boardfile_ibfk_1' FOREIGN KEY ('bfbid') REFERENCES 'board' ('bid')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- attachCount 함수 생성
delimiter $$
CREATE FUNCTION 'board'.'attachCount'(bid int) RETURNS int
begin
declare acount int;
select count(*) into acount from boardfile where bfbid=bid;
return acount;
end $$
delimiter ;







