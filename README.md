자바 ORM 표준 JPA 표준 프로그래밍 - 기본편
------------------------------------------

---

1챕터 - JPA 소개
----------------

### SQL 중심적인 개발의 문제점

-	데이터베이스 세계의 헤게모니

	-	관계형 DB [Oracle, MySQL, ...]
	-	지금 시대는 객체를 관계형 DB에 관리

-	데이테베이스 사용 시 보통 SQL 중심으로 개발됨.

	-	무한 반복, 지루한 코드

		-	CRUD
		-	자바 객체를 SQL로
		-	SQL을 자바 객체로

	-	SQL에 의존적인 개발을 피하기 어렵다.

		-	필드 추가 시 객체, 쿼리 한땀한땀 수정필요

-	패러다임의 불일치 [객체 vs 관계형 데이터베이스]

	-	객체를 영구로 보관하는 다양한저장소

		-	RDB, NoSQL, File, OODB

		> 현실적인 대안은 관계형 데이터베이스

	-	객체를 관계형 데이터베이스에 저장

		-	누가? : 개발자가...

	-	객체와 관계형 데이터베이스의 차이

		-	상속

		-	연관관계

			-	객체는 참조를 사용: member.getTeam()

			-	테이블은 외래 키를 사용 : JOIN ON M.TEAM.ID = T.TEAM_ID

			-	그렇기 때문에 보통 객체를 테이블에 맞춰서 모델링함

			-	객체 다운 모델링을 할경우?

				-	조회 시 문제 발생함 (상당히 번거로움)

				-	처음 실행하는 SQL에 따라 탐색 범위 결정(조회)

				> 엔티티 신뢰문제가 발생함 (조회하는 대상을 직접 확인하지 않는 이상 신뢰할 수 없음)

				-	그렇다고 모든 객체를 미리 로딩할 수는 없음

				> 객체답게 모델링 할수록 매핑 작업만 늘어난다.

		-	데이터 타입

		-	데이터 식별방법

### JPA 소개

-	JPA : Java Persistence API

	-	자바 진영의 ORM 기술 표준

-	ORM? : Object-relational mapping (객체 관계 매핑)

	-	객체는 객체대로 설계
	-	관계형 데이터베이스는 관계형 데이터베이스대로 설계
	-	ORM 프레임워크가 중간에서 매핑
	-	대중적인 언어에는 대부분 ORM 기술이 존재

-	JPA는 애플리케이션과 JDBC 사이에서 동작

-	JPA 동작 - 저장

	-	Entity 분석
	-	INSERT 쿼리 생성
	-	JDBC API 사용해서 DB에 보내고 결과를 받음
	-	**패러다임 불일치 해결**

-	JPA 동작 - 조회

	-	SELECT 쿼리 생성
	-	JDBC API 사용해서 DB에 보내고 결과를 받음
	-	ResultSet 매핑
	-	**패러다임 불일치 해결**

-	JPA는 표준 명세

	-	JPA는 인터페이스의 모음
	-	JPA 2.1 표준 명세를 구현한 3가지 구현체
	-	하이버네이트, EclipseLink, DataNuceleus

-	JPA를 왜 사용해야 하는가?

	-	SQL 중심적인 개발에서 객체 중심으로 개발
	-	생산성

		-	저장: jpa.persist(member)
		-	조회: Member member = jpa.find(memberId)
		-	수정: member.setName("변경이름")
		-	삭제: jpa.remove(member)

	-	유지보수

		-	기존: 필드 변경 시 모든 SQL 수정

		-	JPA: 필드만 추가하면 됨. SQL은 JPA가 처리

	-	패러다임의 불일치 해결

		-	JPA와 상속 (album과 item이 상속관계일 경우)

			-	저장
				-	jpa.persist(album); -> JPA가 ITEM, ALBUM 테이블에 INSERT 쿼리를 날림
			-	조회
				-	Album album = jpa.find(Album.class, albumId); -> 나머진 JPA가 처리 (조인 등)

		-	JPA와 연관관계

			-	연관관계 저장

				-	member.setTeam(team); jpa.persist(member);

		-	JPA와 객체 그래프 탐색

			-	객체 그래프 탐색

				-	Member member = jpa.find(Member.class, memberId); Team team = member.getTeam();

		-	JPA와 비교하기

			-	== 비교 가능 (동일한 트랜잭션에서 조회한 엔티티는 같음을 보장)

	-	성능

		-	1차 캐시와 동일성 보장
			-	같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상
			-	DB Isolation Level 이 Read Commit 이어도 애플리케이션에서 Repeatable Read 보장
		-	트랜잭션을 지원하는 쓰기 지연

			-	트랜잭션을 커밋할 때 까지 INSERT SQL을 모음
			-	JDBC BATCH SQL 기능을 사용해서 한번에 SQL 전송 (옵션키면 비슷한 쿼리들을 한번에 보냄)

		-	지연 로딩

			-	지연로딩: 객체가 실제 사용될 때 로딩 (이것도 옵션이 있음. 특정 객체에 적용가능한듯? ex_멤버를 조회할때는 팀같이 조회)

			-	즉시 로딩: JOIN SQL로 한번에 연관된 객체까지 미리 조회

	-	데이터 접근 추상화와 벤더 독립성

	-	표준

> ORM은 객체와 RDM 두 기둥 위에 있는 기술

---

2챕터 - JPA 시작하기
--------------------

### Hello JPA - 프로젝트 생성

-	H2 데이터베이스 설치와 실행

	-	최고의 실습용 DB
	-	가벼움
	-	웹용 쿼리툴 제공
	-	MySQL, Oracle 데이터베이스 시뮬레이션 가능
	-	시퀀스, AUTO INCREMENT 기능 지원

-	메이븐

	-	자바 라이브러리, 빌드 관리
	-	라이브러리 자동 다운로드 및 의존성 관리
	-	최근에는 그래들이 유명

-	org.hibernate:hibernate-entitymanager:5.3.10.Final

	-	javax.persistence:javax.persistence-api:2.2 : jpa의 인터페이스들이 모여있다.

-	JPA 설정하기 : persistence.xml

	-	JPA 설정파일
	-	/META-INF/persistence.xml (표준위치임)
	-	persistence-unit name으로 이름 지정
		-	보통 데이터베이스당 하나 생성함
	-	javax.persistence로 시작 : JPA 표준 속성

		-	필수설정. DB 관련 설정

	-	hibernate.dialect

		-	데이터베이스 방언
			-	JPA는 특정 데이터베이스에 종속X
			-	각각의 데이터베이스가 제공하는 SQL문법과 함수는 조금씩 다름
				-	VARCHAR, SUBSTRING, LIMIT 등
			-	방언: SQL 표준을 지키지 않는 특정 데이터베이스만의 고유한 기능
			-	JPA가 Dialect 사용 -> 각 DB에맞는 Dialect -> SQL 생성

	-	hibernate로 시작 : 하이버네이트 전용 속성

### Hello JPA - 애플리케이션 개발

-	Persistence class -> persistence.xml 을 읽어서 EntityMangeFactory class 생성 -> 필요할 때마다 EntityManager 를 생성해서 돌림

-	실습 - JPA 동작 확인

	-	JpaMain 클래스 생성
	-	JPA 동작 확인

	-	@Entity 필수 : jpa가 로딩될 때 jpa가 사용하는 것임을 인식함

	-	EntityMangeFactory는 애플리케이션 로딩 시점에 딱 하나만 있어야함.

	-	트랜잭션 단위, 실제 DB 저장 등은 EntityManager 생성

	-	jpa는 트랜잭션이 중요함. 모든 작업은 트랜잭션안에서 작업을 해야함.

	-	jpa를 통해서 가져오면 관리를 함(변경되었으면 commit 할때 체크해서 update 실행)

-	주의

	-	엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
	-	엔티티 매니저는 쓰레드간 공유 X(사용 후 버려야함)
	-	JPA의 모든 데이 변경은 트랜잭션 안에서 실행

-	JPQL 소개

	-	가장 단순한 조회 방법

		-	EntityManager.find()
		-	객체 그래프 탐색(a.getB().getC())

	-	JPA를 사용하면 엔티티 객체를 중으로 개발

	-	문제는 검색 쿼리

	-	검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색

	-	모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능

	-	애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요

	-	JPA는 SQL를 추상화한 JPQL 이라는 객체 지향 쿼리 언어 제

	-	SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원

	-	JPQL은 엔티티 객체를 대상으로 쿼리

	> 방언을 바꿔도 JPA 코드를 변경할 필요가 없다

	-	SQL은 데이터베이스 테이블을 대상으로 쿼리

	-	테이블이 아닌 객체를 대상으로 검하는 객체 지향 쿼리

	-	SQL을 추상화해서 특정 데이터베이스 SQL애 의존 X

	-	JPQL을 한마디로 정의하면 객체 지향 SQL
