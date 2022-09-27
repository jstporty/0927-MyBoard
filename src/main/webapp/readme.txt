* '최범균의 JSP2.3 웹프로그래밍 기초부터 중급까지' 교재를 활용한 강의용 게시판 소스입니다.

- 사용 버젼 : Java11, JEE7, Tomcat9, MySQL8

- 화면 실행 : http://localhost:톰캣포트/MyBoard

- DB스크립트 : /WEB-INF/ddl/ddl.sql

- 업로드한 파일이 위치할 C:/uploadfiles 폴더 필요

- 소스코드 구조
src
	main
		java
			myboard
				constants : 상수값들을 properties파일에 정의하고 읽어옴
				controller : MVC 컨트롤러
				dao : 데이터 처리를하는 DAO
				dto : 데이터 DTO
				handler : Command 핸들러
				service : 요청의 진입점 Service
				test : 테스트용 java
				util : DBCP, File upload/download 유틸
			webapp
				js : javascript
				jsp : jsp
				META-INF : 프로젝트 정보
				WEB-INF
					ddl : 데이터베이스 및 테이블, 함수 생성 쿼리
					lib : 필요 jar
					props : properties 파일들
					web.xml : 웹어플리케이션 설정 파일
			index.jsp : 웹어플리케이션 진입 파일
			readme.txt : 현재 도움말 문서

[만든이 : cons@softenc.com]