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

---

3챕터 - 영속성 관리_내부 동작 방식
----------------------------------

### 영속성 컨텍스트 1

-	JPA를 이해하려면 영속성 컨텍스트를 이해해야함.

-	JPA에서 가장 중요한 2가지

	-	객체와 관계형 데이터베이스 매핑하기 (Object Relational Mapping)
	-	**영속성 컨텍스트**
		-	JPA가 내부에서 어떻게 동작하는가?

-	엔티티 매니저 팩토리와 엔티티 매니저

	-	웹 어플리케이션 -> 엔티티 매니저 팩토리 -> 요청이 욜때마다 엔티티 매니저를 생성 -> 매니저를 통해 내부적으로 데이터베이스 커넥션을 사용해서 DB를 사용함

-	영속성 컨텍스트

	-	JPA를 이해하는데 가장 중요한 용어
	-	'엔티티를 영구 저장하는 환경'이라는 뜻
	-	EntityManager.persist(entity);
		-	DB에 저장하는 것이 아니라 엔티티를 영속성컨텍스트라는 곳에 저장한다는 뜻임

-	엔티티 매니저? 영속성 컨텍스트?

	-	영속성 컨텍스트는 논리적인 개념
	-	눈에 보이지 않음
	-	엔티티 매니저를 통해 영속성 컨텍스트에 접근함
		-	(J2SE환경) 엔티티 매니저를 생성하면 1:1로 영속성 컨텍스트가 생성됨.
		-	스프링 프레임워크는 개념이 조금 달라짐.

-	엔티티의 생명주기

	-	비영속(new/transient) : 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태

		-	객체를 생성한 상태 (비영속)

		```java
		Member member = new Member();
		member.setId("member1");
		```

	-	영속(managed) : 영속성 컨텍스트에 관리되는 상태 (EntityManager.persist(entity);)

		-	객체를 저장한 상태 (이 때 쿼리가 날라가는게 아님!)

		```java
		em.persist(member);
		```

	-	준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태

		-	엔티티를 영속성 컨텍스트에서 분리

		```java
		em.detach(member);
		```

	-	삭제(removed) : 삭제된 상태

		-	객체를 삭제한 상태

		```java
		em.remove(member);
		```

### 영속성 컨텍스트 2

-	영속성 컨텍스트의 이점 (데이터베이스와 어플리케이션과 중간계층이 있음.)

	-	1차 캐시

		-	영속성 컨텍스트 내부에 1차 캐시라는 것을 들고있음.
		-	키가 @ID, 값이 Entity(객체) 라고 생각면 됨.
		-	조회 시 영속성 컨텍스트에서 먼저 1차 캐시에서 찾음
		-	1차 캐시에 없는 경우 DB에서 조회 -> 1차 캐시에 저장 -> 반환
		-	비즈니스가 끝나면 영속성 컨텍스트를 지움(1차캐시도 날라감). 아주 짧은 시간에서만 이점이기 때문에 엄청난 큰 이점은 아님.

		```java
		...
		em.persist(member);


		Member findMember = em.find(Member.class, 101L);


		// select 쿼리가 찍히지 않고 print 찍히는 것을 알 수 있음 (1차캐시에서 조회))
		System.out.println("findMember.id = " + findMember.getId());


		tx.commit();
		```

	-	동일성 보장

		-	1차 캐시로 반복가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공

		```java
		Member member1 = em.find(Member.class, "member1");
		Member member2 = em.find(Member.class, "member1");


		// 동일성 비교 true
		System.out.println(a == b);
		```

	-	트랜잭션을 지원하는 쓰기 지연

		-	em.persist(memberA); 이 떄는 INSERT SQL를 보내지 않고 JPA가 쌓고 있다가 tx.commit(); 하는순간에 SQL를 보낸다.
		-	영속성 컨텍스트에는 쓰기 지연 SQL 저장소라는 게 있음.
		-	persist 하면 1차캐시에 저장하고, JPA가 INSERT 쿼리를 생성해서 쓰기 지연 SQL저장소에 저장함.
		-	commit 하면 flush를 하면서 SQL이 날라감.

	-	변경 감지

		-	수정 시 set만 해주면 된다.
		-	commit 하는 시점에 flush가 호출되는데, 1차 캐시 안의 엔티티와 스냅샷(값을 읽어온 최초시점의 값)을 비교함.
		-	비교하고 변경됬으면 update 쿼리를 쓰기지연 SQL 저장소에 만들어둠.

		```java
		Member member1 = em.find(Member.class, "member1");


		member1.setUserName("hi");
		member1.setAge(10);


		// 해당 코드가 필요 없다.
		//em.update(member1);


		tx.commit();
		```

	-	지연로딩

		-	나중에 뒤에서 다시 설명

### 플러시

-	1차 캐시를 지우진 않음.
-	영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
-	트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 됨
-	변경감지 & 쓰기지연 SQL저장소에 있는 쿼리들을 데이터베이스에 반영이 되는 과정이다.

-	영속성 컨텍스트를 플러시하는 방법

	-	직적 호출 : em.flush()
	-	플러시 자동 호출 : 트랜잭션 커밋
	-	플러시 자동 호출 : JPQL 쿼리 실행 (중간에 JPQL이 실행됐을 때를 대비?)

-	플러시 모드 옵션

	-	em.setFlushMode(FlushModeType.COMMIT)
	-	FlushModeType.AUTO : 커밋이나 쿼리를 실행할 때 플러시 (기본값. 가급적 그냥 사용할 것)
	-	FlushModeType.COMMIT : 커밋할 때만 플러시 (중간에 플러시해도 이점이 없는 경우 사용?)

### 준영속 상태

-	준영속?

	-	영속 -> 준영속
	-	영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
	-	영속성 컨텍스트가 제공하는 기능을 사용 못함

-	준영속 상태로 만드는 방법

	-	em.detach(entity) : 특정 엔티티만 준영속 상태로 전환
	-	em.clear() : 영속성 컨텍스트를 완전히 초기화 (테스트 시 쿼리 확인하고 싶을 때 도움이 됌)
	-	em.close() : 영속성 컨텍스트를 종료

---

4챕터 - 엔티티 매핑
-------------------

### 객체와 테이블 매핑

-	JPA 에서 제일 중요하게 봐야하는 것 2가지

	-	영속성 컨텍스트 / JPA 내부동작방식
	-	실제 설계적인 측면

-	엔티티 매핑 소개

	-	객체와 테이블 매핑 : @Entity, @Table
	-	필드와 컬럼 매핑 : @Column
	-	기본 키 매핑 : @Id
	-	연관관계 매핑 : @ManyToOne, @JoinColumn

-	@Entity

	-	@Entity가 붙은 클래스는 JPA가 관리, 엔티리 라 한다.
	-	JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
	-	주의

		-	기본 생성자 필수 (파라미터가 없는 public 또는 protected 생성자)
		-	final 클래스, enum, interface, inner 클래스 사용 x
		-	저장할 필드에 final 사용 x

	-	속성 정리

		-	속성 : name ( @Entity(name = "Member") )
			-	JPA에서 사용할 엔티티 이름을 지정한다.
			-	기본 값 : 클래스 이름 그대로 사용
			-	같은 클래스 이름이 없으면 가급적 기본 값을 사용한다.

-	@Table

	-	@Table은 엔티티와 매핑할 테이블 지정

### 데이터베이스 스키마 자동 생성

-	JPA에서는 애플리케이션 로딩 시점에 DB 테이블 생성하는 기능도 지원해줌 (운영에서는 쓰면안됨. 개발 단계에서 도움이 됌)

-	데이터베이스 스키마 자동 생성

	-	DDL을 애플리케이션 실행 시점에 자동 생성

	-	테이블 중심 -> 객체 중심

	-	데이터베이스 방언을 활용해서 데이터베이스에 맞는 적잘한 DDL 생성

	-	이렇게 생성된 DDL은 개발 장비에서만 사용

	-	생성된 DDL은 운영서버에서는 작동하지 않거나, 적절히 다듬은 후 사용

-	hibernate.hbm2ddl.auto

	-	create : 기존 테이블 삭제 후 다시 생성 (DROP + CREATE)
	-	create-drop : create와 같으나 종료시점에 테이블 DROP
	-	update : 변경분만 반영(운영 DB에는 사용하면 안됨)

		-	추가 시 alter 실행함
		-	지우는건 안됨

	-	validate : 엔티티와 테이블이 정상 매핑되었는지만 확인

		-	컬럼이 맞지않으면 오류 발생

	-	none : 사용하지 않음 (혹은 해당 옵션 주석처리)

	```java
	<property name="hibernate.hbm2ddl.auto" value="create" />
	```

-	데이터베이스 스키마 자동 생성 - 주의

	-	운영 장비에는 절대 create, create-drop, update 사용하면 안된다.

	-	개발 초기 단계는 create 또는 update

	-	테스트 서버는 update 또는 validate

		-	drop 으로 인해 데이터가 날라갈 수 있음
		-	validate 까지는 괜찮음.. update는 alter가 자동으로 실행되기 떄문에 조심해야함 (데이터가 많은 경우 alter로 디비가 중지될 수 있음)

	-	스테이징과 운영서버는 validate 또는 none

-	DDL 생성 기능

	-	제약 조건 추가

		-	@Column(nullable = false, length =10) : 회원 이름은 필수, 10자 초과 X

	-	유니크 제약조건 추가

		-	@Table(uniqueConstraints = {@UniqueConstraint(name ="NAME_AGE_UNIQUE", columnNames={"NAME", "AGE"})})

	-	DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.

### 필드와 컬럼 매핑

-	요구사항 추가

	-	회원은 일반 회원과 관리자로 구분해야 한다.
	-	회원 가입일과 수정일이 있어야 한다.
	-	회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.

-	매핑 어노테이션 정리

	-	@Column : 컬럼 매핑

		-	name : 필드와 매핑할 테이블의 컬럼 이름 (기본 값: 객체의 필드이름)
		-	insertable, updatable : 이 컬럼을 등록, 변경 가능 여부 (기본 값 : TRUE)
		-	nullable(DDL): null 값의 허용여부를 설정한다. false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다.
		-	unique(DDL) : @Table의 uniqueConstraints와 같지만 한 컴럼에 간단히 유니크 제약 조건을 걸 때 사용한다. (잘 안쓰임. 이름이 랜덤값임.. 대신 @Table에서 걸면 이름 지정 가능)
		-	columnDefinition : 데이터베이스 컬럼 정보를 직접 줄 수있다.
			-	columnDefinition= "varchar(100) default 'EMPTY'"
		-	length(DDL) : 문자 길이 제약조건, String 타입에만 사용한다. (기본 값 : 255)
		-	precision, scale(DDL) : BigDecimal 타입에서 사용한다.(BigInteger도 사용가능). precision은 소수점을 포함한 전체 자릿수를, scale은 소수의 자릿수다. 참고로 double, float 타입에는 적용되지 않는다. 아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다. (기본 값 : precision = 19, scale=2)

	-	@Temporal : 날짜 타입 매핑

		-	날짜 타입 (java.util.Date, java.util.Calendar)을 매핑할 때 사용
			-	TemporalType.DATE : 2013-10-11
			-	TemporalType.TIME : 11:11:11
			-	TemporalType.TIMESTAMP : 2013-10-11 11:11:11
		-	LocalDate, LocalDateTime을 사용할 때는 생략 가능

	-	@Enumerated : enum 타입 매핑

		-	자바 enum 타입을 매핑할 때 사용 (기본 값이 ORDINAL)
		-	**ORDINAL 사용 X**
		-	EnumType.ORDINAL : enum 순서를 데이터베이스에 저장 / EnumType.STRING : enum 이름을 데이터베이스에 저장
		-	운영 중간에 enum 값 추가에 따라 혼란이 올 수 있음 (0 자리에 값이 변경 등)

	-	@Lob : BLOB, CLOB 매핑

		-	데이터베이스 BLOB, CLOB 타입과 매핑
		-	@Lob에는 지정할 수 있는 속성이 없다.
		-	매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
			-	CLOB : String, char[], java.sql.CLOB
			-	BLOB : byte[], java.sql.BLOB

	-	@Transient : 특정 필드를 컬럼에 매핑하지 않음 (매핑 무시)

		-	필드 매핑 X
		-	데이터베이스에 저장 X, 조회 X
		-	주로 메모리 상에서만 임시로 어떤 값을 보관하고 싶을 때 사용

### 기본 키 매핑

-	기본 키 매핑 어노테이션

	-	@Id

	-	@GeneratedValue

-	기본 키 매핑 방법

	-	직접 할당 : @Id만 사용
	-	자동 생성(@GeneratedValue)

		-	IDENTITY : 데이터베이스에 위임, MYSQL

			-	기본 키 생성을 데이터베이스에 위임
			-	주로 MySQL, PostgreSQL, SQL Server, DB2 에서 사용 (MySQL의 AUTO_INCREMENT)
			-	JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
			-	AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
			-	IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자 조회

		-	SEQUENCE : 데이터베이스 스퀀스 오브젝트 사용, ORACLE

			-	데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트(ex_ 오카르 시퀀스)
			-	오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용
			-	em.persist 전에 DB에서 값을 얻어옴 (call next value for MEMBER_SEQ) (버퍼링 가능)
			-	insert 마다 call을 하면 성능문제가 걱정될 수 있음. allocationSize 을 통해서 미리 50개의 사이즈를 가져옴 (DB에 미리 올려놓고, 메모리에서 개수많큼 쓰는 방식. 동시성 이슈없음)
			-	테이블마다 시퀀스를 따로 관리하고 싶으면 @SequenceGenerator 사용

				```text
				@Entity
				@SequenceGenerator(
				        name = “MEMBER_SEQ_GENERATOR",
				        sequenceName = “MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
				        initialValue = 1, allocationSize = 50)
				public class Member {
				        @Id
				        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
				        private Long id;
				```

		-	TABLE : 키 생성요 테이블 사용, 모든 DB에서 사용

			-	키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
			-	장점 : 모든 데이터베이스에 적용 가능
			-	단점 : 성능
			-	allocationSize SEQUENCE전략과 동일
			-	@TableGenerator 필요

				```text
				@Entity
				@TableGenerator( name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES", pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
				public class Member {
				    @Id
				    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
				    private Long id;
				```

		-	AUTO : 방언에 따라 자동 지정, 기본 값

-	권장하는 식별자 전략

	-	키본 키 제약 조건 : null 아님, 유일, 변하면 안된다.
	-	미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
	-	예를 들어 주민등록번호도 기본 키로 적절하지 않다.
	-	권장 : Long형 + 대체키 + 키 생성전략 사용

### 실전 예제 - 1. 요구사항 분석과 매핑

-	요구사항 분석

	-	회원은 상품을 주문할 수 있다.
	-	주문 시 여러 종류의 상품을 선택할 수 있다.

-	기능 목록

	-	회원 기능

		-	회원등록
		-	회원조회

	-	상품 기능

		-	상품등록
		-	상품수정
		-	상품조회

	-	주문 기능

		-	상품주문
		-	주문내역조회
		-	주문취소

-	도메인 모델 분석

	-	회원과 주문의 관계 : 회원은 여러 번 주문할 수 있다. (일대다)
	-	주문과 상품의 관계 : 주문할 때 여러 상품을 선택할 수 있다. 반대로 상품도 여러 번 주문될 수 있다. 주문상품이라는 모델을 다대다 관계를 일대다, 다대일 관계로 풀어냄

-	테이블 설계

	-	제약조건을 추가하는 것을 선호한다고함(DB탭 보지않고 확인가능)

-	데이터 중심 설계의 문제점

	-	현재 방식은 객체 설계를 테이블 설계에 맞춘 방식
	-	테이블의 외래키를 객체에 그대로 가져옴
	-	객체 그래프 탐색이 불가능
	-	참조가 없으므로 UML도 잘못됨

---

5챕터 - 연관관계 매핑 기초
--------------------------

### 단방향 연관관계

-	좀 더 객체지향적으로 설계하자

-	목표

	-	객체와 테이블 연관관계의 차이를 이해
	-	객체의 참조와 테이블의 외래 키를 매핑
	-	용어 이해
		-	방향: 단방향, 양방향
		-	다중성: 다대일, 일대다, 일대일, 다대다 이해
		-	**연관관계의 주인**: 객체 양방향 연관관계는 관리 주인이 필요

-	연관관계가 필요한 이유

	-	객체지향 설계의 목표는 자율적인 객체들의 협력 공동체를 만드는 것이다.

-	예제 시나리오

	-	회원과 팀이 있다.
	-	회원의 하나의 팀엠나 소속될 수 있다.
	-	회원과 팀은 다대일 관계다.
