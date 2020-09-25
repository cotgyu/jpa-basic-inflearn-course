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
	-	회원의 하나의 팀에만 소속될 수 있다.
	-	회원과 팀은 다대일 관계다.

-	객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다.

	-	테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다.
	-	객체는 참조를 사용해서 연관된 객체를 찾는다.
	-	테이블과 객체 사이에는 이런 큰 간격이 있다.

### 양방향 연관관계와 연관관계의 주인 1 - 기본

-	**이 강좌에서 제일 중요한 부분임**

-	양방향 매핑

	-	테이블간의 연관관계는 외래키 하나로 양방향이 다 있다고 볼 수 있음.

-	연관관계 주인과 mappedBy

	-	mappedBy = JPA의 멘탈붕괴 난이도
	-	mappedBy는 처음에는 이해하기 어렵다.
	-	객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.

		-	객체 연관관계 = 2가지

			-	회원 -> 팀 연관관계 1개 (단방향)
			-	팀 -> 회원 연관관계 1개(단방향)

		-	테이블 연관관계 = 1개

			-	회원 <-> 팀의 연관관계 1개(양방향) (외래키 값 하나로 알 수 있다)

-	객체의 양방향 관계

	-	객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개다.
	-	객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.

-	테이블의 양방향 연관관계

	-	테이블은 외래 키 하나로 두 테입르의 연관관계를 관리
	-	MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계 가짐 (양쪽으로 조인할 수 있다.)

-	둘 중 하나로 외래 키를 관리해야 한다.

-	연관관계의 주인(Owner)

	-	양방향 매핑 규칙
		-	객체의 두 관계 중 하나를 연관관계의 주인으로 지정
		-	**연관관계의 주인만이 외래 키를 관리(등록, 수정)**
		-	**주인이 아닌 쪽은 읽기만 가능**
		-	주인은 mappedMy 속성 X
		-	주인이 아니면 mappedBy 속성으로 주인 지정

-	누구를 주인으로?

	-	**외래 키가 있는 곳을 주인으로 정해라** (외래키가 있는 곳이 다(N) 임. 다(N) 쪽이 연관관계 주인이 됨. 알맞은 테이블에 업데이트가 나가서 직관적임.)
	-	여기서는 Member.team이 연관관계의 주인

### 양방향 연관관계와 연관관계의 주인 2 - 주의점, 정리

-	양방향 매핑 시 가장 많이 하는 실수 : 연관관계의 주인에 값을 입력하지 않음

	```java
	// 실수
	Team team = new Team(); 
	team.setName("TeamA"); 
	em.persist(team); 


	Member member = new Member(); 
	member.setName("member1"); 
	//역방향(주인이 아닌 방향)만 연관관계 설정 
	team.getMembers().add(member); 
	em.persist(member); 


	// 정상
	Team team = new Team(); 
	team.setName("TeamA"); 
	em.persist(team); 


	Member member = new Member(); 
	member.setName("member1"); 
	member.setTeam(team)
	em.persist(member); 
	```

-	양방향 매핑 시 연관관계의 주인에 값을 입력해야 한다. : 순수한 객체 관계를 고려하면 항상 양쪽 다 값을 입력해야한다.

	-	문제점
		-	flush, clear 를 안할 경우 DB에서 select 쿼리가 안나가기 때문에 객체에서는 셋팅을 해줬지만, 셋팅된 내용이 반영이 안된 상태로 사용될 수 있음.
		-	테스트 케이스 작성 시 JPA를 사용하지 않을 수 있는데, 두 객체가 달라 맞지않는 문제가 생길 수 있음 (양쪽 다 셋팅해줘야함!)

	```java
	Team team = new Team(); 
	team.setName("TeamA"); 
	em.persist(team); 


	Member member = new Member(); 
	member.setName("member1"); 


	team.getMembers().add(member); //연관관계의 주인에 값 설정
	member.setTeam(team); //** 


	em.persist(member); 
	```

-	양방향 연관관계 주의 - 실습

	-	**순수 객체 상태를 고려해서 양쪽에 값을 설정하자**
	-	연관관계 편의 메서드를 생성하자 (둘 중 한 곳에만 넣는 것을 추천)

		```java
		// getter setter 관례가 있기 때문에 메서드이름은 변경을 추천함
		// 기타 비즈니스 로직이 생성될 수 있음
		public void changeTeam(Team team){
		    this.team = team;
		    team.getMember().add(this);
		}
		```

	-	양방향 매핑 시 무한 루프를 조심하자

		-	ex_ toString(), lombok, JSON 생성 라이브러리
		-	컨트롤러에서는 엔티티를 절대 반환하지 말 것
			-	무한루프생성 가능성 있음, 엔티티를 변경하는 순간 API 스펙이 변경되어버림
			-	**엔티티는 DTO로 변환 후 사용할 것!**

		```java
		// Member
		@Override
		public String toString() {
		return "Ex_Member{" +
		        "id=" + id +
		        ", username='" + username + '\'' +
		        ", team=" + team + // 매핑된 양쪽으로 toString을 호출할 수 있음
		        '}';
		}


		// Team
		@Override
		public String toString() {
		return "Ex_Team{" +
		        "id=" + id +
		        ", name='" + name + '\'' +
		        ", members=" + members + // 매핑된 양쪽으로 toString을 호출할 수 있음
		        '}';
		}
		```

-	양방향 매핑 정리

	-	**단방향 매핑만으로도 이미 연관관계 매핑은 완료** (최초 설계 시 단방향 매핑으로 끝낼 것!)
	-	양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
	-	JPQL에서 역방향으로 탐색할 일이 많음(편하게 사용하기 위해 양방향을 필요할 때 있음...)
	-	단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨 (테이블에 영향을 주지 않음)

-	연관관계의 주인을 정하는 기준

	-	비즈니스 로직을 기준으로 연관관계의 주인을 선택하면 안됨
	-	**연관관계의 주인은 외래 키의 위치를 기준으로 정해야함!!**
		-	운영기준 관점에서도 이점이 많음

### 실전 예제 2 - 연관관계 매핑 시작

-	테이블 구조는 이전과 같음

-	객체구조는 참조를 사용하도록 변경

---

6챕터 - 다양한 연관관계 매핑
----------------------------

### 다대일 [N:1]

-	연관관계 매핑 시 고려사항 3가지

	-	다중성
		-	다대일 : @ManyToOne
		-	일대다 : @OneToMany
		-	일대일: @OneToOne
		-	다대다 : @ManyToMany (실무에서 쓰면 안됨)
	-	단방향, 양방향

		-	테이블

			-	외래 키 하나로 양쪽 조인 가능
			-	사실 방향이라는 개념이 없음

		-	객체

			-	참조용 필드가 있는 쪽으로만 참조 가능
			-	한쪽만 참조하면 단방향
			-	양쪽이 서로 참조하면 양방향

	-	연관관계의 주인

		-	테이블은 외래 키 하나로 두 테이블이 연관관계를 맺음
		-	객체 양방향 관계는 A-B, B->A 처럼 참조가 2군데
		-	객체 양방향 관계는 참조가 2군데 있음. 둘 중 테이블의 외래키를 관리할 곳을 지정해야함
		-	연관관계의 주인 : 외래 키를 관리하는 참조
		-	주인의 반대편 : 외래 키에 영향을 주지 않음, 단순 조회만 가능

-	대대일 [N:1]

	-	다 쪽에 외래키가 있음

	-	다대일 단방향 정리

		-	가장 많이 사용하는 연관관계
		-	다대일의 반대는 일대다

	-	다대일 양방향

		-	다 가 연관관계 주인
		-	어차피 읽기만 가능함
		-	테이블에 영향을 주진 않음

### 일대다 [1:N]

-	여기서는 일 이 연관관계 주인임

-	일대다

	-	이 모델은 권장하지 않음. 하지만 표준스펙에서 지원함.

		-	team에 조작을 헀는데, member에 update가 발생함. 운영할 때 힘들어짐.

	-	일대다 단방향 정리

		-	일대다 단방향은 일대다(1:N)에서 1이 연관관계의 주인
		-	테이블 일대다 관게는 항상 다(N)쪽에 외래 키가 있음
		-	**객체와 테이블 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조**
		-	@JoinColumn을 꼭 사용해야 함. 그렇지 않으면 조인 테이블 방식을 사용함(중간에 테이블을 하나 추가함)

			-	성능 애매, 운영 힘들어짐..

		-	일대다 단방향 매핑의 단점

			-	엔티티가 관리하는 외래 키가 다른 테이블에 있음
			-	연관관계의 관리를 위해 추가로 UPDATE SQL 실행

		-	**일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자**

	-	일대다 양방향 정리

		-	이런 매핑은 공식적으로 존재하지 않음
		-	@JoinColumn(insertable= false, updatable = false)
		-	읽기 전용 필드를 사용해서 양방향 처럼 사용하는 방법
		-	**다대일 양방향을 사용하자**

### 일대일 [1:1]

-	일대일 관계

	-	반대도 일대일
	-	주 테이블이나 대상 테이블 중에 외래 키 선택 가능'
		-	주 테이블에 외래 키
		-	대상 테이블에 외래 키
	-	외래 키에 데이터베이스 유니크 제약조건 추가

	-	일대일: 주 테이블에 외래 키 양방향 정리

		-	다대일 양방향 매핑처럼 외래 키가 있는 곳이 연관관계의 주인
		-	반대편은 mappedBy 적용

	-	일대일 : 대상 테이블에 외래 키 단방향 정리

		-	단방향 관계는 JPA 지원X
		-	양방향 관계는 지원

	-	일대일 : 대상 테이블에 외래 키 양방향

		-	사실 일대일 주 테이블에 외래 키 양방향과 매핑 방법은 같음

-	일대일 정리

	-	주 테이블에 외래 키

		-	주 객체가 대상 객체의 참조가 가지는 것 처럼 주 테이블에 외래 키를 두고 대상 테이블을 찾음
		-	객체지향 개발자 선호
		-	JPA 매핑 편리
		-	장점 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
		-	단점 : 값이 없으면 외래 키에 null 허용

	-	대상 테이블에 외래 키

		-	대상 테이블에 외래 키가 존
		-	전통적인 데이터베이스 개발자 선호
		-	장점 : 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
		-	단점 : 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨
			-	member에 locker 있는 지 보려면 이 관계에서는 locker를 조회해서 memberid 있는지 확인해야함.

### 다대다 [N:N]

-	다대다

	-	관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없음
	-	연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야함
	-	**객체는 컬렉션을 사용해서 객체 2개로 다대 관계 가능**

	-	@ManyToMany 사용

	-	@JoinTable로 연결 테이블 지정

	-	다대다 매핑 : 단방향, 양방향 가능

-	다대다 매핑의 한계

	-	편리해 보이지만 실무에서 사용 X
	-	연결 테이블이 단순히 연결만 하고 끝나지 않음
	-	주문시간, 수량 같은 데이터가 들어올 수 있음

-	다대다 한계 극복

	-	연결 테이블용 엔티티 추가 (연결테이블을 엔티티로 승격)
	-	@ManyToMany -> @OneToMany, @ManyToOne
	-	(연결 테이블의 pk는 묶어서하지말고 그냥 새로운 값으로 할 것. 유연성이 생길 수 있음. 매핑도 편함. 필요하면 제약조건을 따로 추가)

### 실전 예제 3 - 다양한 연관관계 매핑

-	배송, 카테고리 추가 - 엔티티

	-	주문과 배송은 1:1(@OneToOne)
	-	상품과 카테고리는 N:M (@ManyToMany)

-	N:M 관계는 1:N, N:1 로

	-	테이블의 N:M 관계는 중간 테이블을 이용해서 1:N, N:1
	-	실전에서는 중간 테이블이 단순하지 않다.
	-	@ManyToMany는 제약 : 필드 추가 X, 엔티티 테이블 불일치
	-	실전에는 @ManyToMany 사용 X

-	@JoinColumn

	-	외래 키를 매핑할 때 사용
	-	외래 키가 참조하는 대상 테이블의 컬럼명이 달라질 경우 referencedColumnName 사용할 것

---

7챕터 - 고급매핑
----------------

### 상속 관계 매핑

-	상속관계 매핑

	-	관계형 데이터베이스는 상속 관계 X
	-	슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사
	-	상속관계 매핑 : 객체의 상속과 구조와 DB의 슈퍼타입 서브타입 관계를 매핑

-	슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법 (어떤 방법이든 JPA는 매핑이 가능함)

	-	각각 테이블로 변환 -> 조인 전략
	-	통합 테이블로 변환 -> 단일 테이블 전략 (논리모델을 한 테이블로 합친다)
	-	서브타입 테이블로 변환 -> 구 현 클래스마다 테이블 전략

-	주요 어노테이션 (어노테이션으로 컨트롤 가능)

	-	@Inheritance(strategy=InheritanceType.XXX)
		-	JOIND : 조인 전략 (기본적인 정석)
		-	SINGLE_TABLE : 단일 테이블 전략
		-	TABLE_PER_CLASS : 구현 클래스마다 테이블 전략 **(추천X)**
			-	조회 시 테이블을 다 찾아봐야함... 복잡한 쿼리생성됨
	-	@DiscriminatorColumn(name="DTYPE")
		-	단일테이블에는 이 어노테이션 없어도 DTYPE이 자동으로 생성됨.
	-	@DiscriminatorVlaue("XXX")

-	조인 전략 장단점

	-	장점
		-	테이블 정규화
		-	외래키 참조 무결성 제약 조건 활용가능
		-	저장공간 효율화
	-	단점
		-	조회 시 조인을 많이사용, 성능 저히
		-	조회 쿼리가 복잡함
		-	데이터 저장시 INSERT SQL 2번 호출 (생각보다 큰 단점은 아님)

-	단일 테이블 전략 장단점

	-	장점

		-	조인이 필요 없으므로 일반적으로 조회 성능이 빠름
		-	조회 쿼리가 단순함

	-	단점

		-	자식 엔티티가 매핑한 컬럼은 모두 null 허용
		-	단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있고, 상황에 따라서 조회성능이 오히려 느려질 수있다.

-	구현 클래스마다 테이블 전략 장단점

	-	**데이터베이스 설계자와 ORM 전문가 둘다 추천하지 않음**

	-	장점

		-	서브 타입을 명확하게 구분해서 처리할 때 효과적
		-	not null 제약조건 사용 가능

	-	단점

		-	여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION SQL)
		-	자식 테이블을 통합해서 쿼리하기 어려움

-	보통 조인전략으로 진행. 너무 단순하고 확장할 일이 없을 경우 단일 테이블 전략 사용하는 경우도 있음. (중요하고 복잡하면 조인전략!)

### MappedSuperclass

-	공통 매핑정보가 필요할 때 사용 (id, name)

	-	id, name 이 테이블마다 계속나옴. 이때 id와 name을 상속받아서 쓰고싶을 때

-	@MappedSuperclass

	-	상속관계 매핑 X
	-	엔티티 X, 테이블과 매핑 X
	-	부모 클래스를 상속 받는 자식클래스에 매핑정보만 제공
	-	조회, 검색 불가 (em.find(BaseEntity) X)
	-	직접 생성해서 사용할 일이 없으므로 추상 클래스 권장

	-	테이블과 관계없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할

	-	주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용

	-	참고 : @Entity 클래스는 엔티티나 @MappedSuperclass로 지정한 클래스만 상속 가능

### 실전 예제 4 - 상속관계 매핑

-	요구사항 추가

	-	상품의 종류는 음반, 도서, 영화가 있고 이후 더 확장될 수 있다.
	-	모든 데이터는 등록일과 수정일이 필수다.

-	실무에선?

	-	복잡도에 대해 고려를 많이 하고 상속관계를 사용할 지 선택해야함

---

8챕터 - 프로식와 연관관계 관리
------------------------------

### 프록시

-	Member를 조회할 때 Team도 함께 조회해야 할까?

	-	Member만 쓰고싶은데 Team도 같이 조회된다?

-	프록시 기초

	-	em.find() vs em.getReference()
	-	em.find() : 데이터베이스를 통해서 실제 엔티티 객체 조회
	-	em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
		-	진짜를 주는게 아니라 하이버네이트가 내부 라이브러리를 써서 프록시 엔티티를 줌
		-	DB에 쿼리가 안나가는데 객체가 조회된다.

-	프록시 특징

	-	실제 클래스를 상속받아서 만들어짐
	-	실제 클래스와 겉모양이 같다
	-	사용하는 입장에서 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨(이론상)
	-	프록시 객체는 실제 객체의 참조(target)를 보관
	-	프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출

	-	프록시 객체의 초기화

		```java
		Member member = em.getReference(Member.class, "id1");
		member.getName();
		```

		-	getName 호출 -> Member target에 값이 없음 -> JPA가 영속성 컨텍스트에 요청함 -> 영속성 컨텍스트가 DB조회 -> 실제 Entity 객체 생성, Member target에 연결시켜줌 -> Member의 getName 값 줌

	-	프록시 객체는 처음 사용할 때 한 번만 초기화

	-	프록시 객체를 초기화 할 때, **프록시 객체가 실제 엔티티로 바뀌는 것은 아님.** 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근가능

	-	**프록시 객체는 원본 엔티티를 상속받음. 따라서 타입 체크 시 주의해야함** ( == 비교실패, 대신 , instance of 사용)

	-	**영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.Reference()를 호출해도 실제 엔티티 반환**

	-	영속성 컨텍스틍의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생 (하이버네이트는 org.hibernate.LazyInitializationException 예외 발생)

		-	보통 트랜잭션 끝나고 프록시 조회할 때 많이 발생함!!

-	프록시 확인

	-	프록시 인스턴스의 초기화 여부 확인 emf.getPersistenceUnitUtil().isLoaded(Ojbect entity)

	-	프록시 클래스 확인방법 entity.getClass.getName() (...javasist.. or HibernateProxy...)

	-	프록시 강제 초기화 org.hibernate.Hibernate.initialize(entity);

	-	참고 : JPA 표준은 강제 초기화 없음. 위에껀 Hibernate가 제공하는 것. 강제 호출을 통해야함. member.getName()

> getReference은 사실 잘 안씀. 즉시 로딩과 지연로딩을 잘 이해하려면 프록시 메커니즘을 이해해야함.

### 즉시 로딩과 지연 로딩

-	Member를 조회할 때 Team도 함께 조회해야할까?

	-	단순히 member 정보만 사용하는 비즈니스 로직

-	지연로딩 LAZY을 사용해서 프록시로 조회

	-	FetchType.LAZY

-	지연로딩

	-	로딩 > 지연로딩 LAZY(team) -> 프록시 엔티티 가져옴

-	지연로딩 LAZY을 사용해서 프록시로 조회

	-	실제 team을 사용하는 시점에 초기화됨(DB 에서 조회!)

-	Member와 Team을 자주 함께 사용한다면?

	-	즉시 로딩 EAGER를 사용해서 함께 사용
	-	JPA 구현체는 가능하면 조인을 사용해 SQL 한번에 함꼐 조회

-	프록시와 즉시로딩 주의

	-	**가급적 지연 로딩만 사용(특히 실무에서)**
	-	즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
	-	**즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.**
	-	**@ManyToOne, @OneToOne은 기본이 즉시 로딩 -> LAZY 로 설정할 것**
	-	@OneToMany, @ManyToMany는 기분이 지연로딩
	-	fetchJoin이 대안

-	지연 로딩 활용

	-	이론적인 내용. 실무에선 지연로딩 사용할 것!!
	-	Member와 Team은 자주 함께 사용 -> 즉시 로딩
	-	Member와 Order는 가끔 사용 -> 지연 로딩
	-	Order와 Product는 자주 함께 사용 -> 즉시로딩

-	지연 로딩 활용 - 실무

	-	**모든 연관관계에서 지연 로딩을 사용해라!!**
	-	실무에서 즉시 로딩을 사용하자 마라!
	-	JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라
	-	즉시 로딩은 상상하지 못한 쿼리가 나간다.

### 영속성 전이 : CASCADE

-	영속성 전이 : CASCADE

	-	특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때
	-	ex_ 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장

-	영속성 전이 : 저장

	-	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)

-	영속성 전이 : CASCADE - 주의!

	-	영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없음
	-	엔티티를 영속화할 때 연관된 엔티티도 함꼐 영속화하는 편리함을 제공할 뿐

-	CASCADE의 종류

	-	**ALL : 모두 적용**
	-	**PERSIST : 영속**
	-	REMOVE : 삭제
	-	MERGE : 병합
	-	REFRESH
	-	DETCH

-	언제쓰는가?

	-	일대다에 모두? X -> 하나의 부모가 자식들을 관리할 때는 유용함 (다른 부모가 있으면 안됨. 운영이 힘들어진다고함..)

-	고아 객체

	-	고아 객체 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
	-	orphanRemoval = true

-	고아 객체 - 주의

	-	참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능
	-	**참조하는 곳이 하나일 때 사용해야함!!**
	-	**특정 엔티티가 개인 소유할 때 사용**
	-	@OneToOne, @OneToMany만 가능
	-	참고: 개념적으로 부모를 제거하면 자식은 고아가 된다. 따라서 고아 객체 제거 기능을 활성화하면, 부모를 제거할 때 자식도 함께 제거된다. 이것은 CascadeType.REMOVE 처럼 동작한다.

-	영속성 전이 + 고아 객체, 생명주기

	-	CascadeType.ALL + orphanRemoval = true
	-	스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
	-	두 옵션을 모두 활성화하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있음 (dao나 repository가 필요 없다!)
	-	도메인 주도 설계(DDD)의 Aggregate Root 개념을 구현할 때 유용

### 실전 예제 5 - 연관관계 관리

-	글로벌 페치 전략 설정

	-	모든 연관관계를 지연 로딩으로
	-	@ManyToOne, @OneToOne은 기본이 즉시 로딩이므로 지연로딩으로 변경

-	영속성 전이 설정

	-	Order -> Delivery를 영속성 전이 ALL 설정
	-	Order -> OrderItem을 영속성 전이 ALL 설정
