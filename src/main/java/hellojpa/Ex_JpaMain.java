package hellojpa;

import hellojpa.domain.Member;
import hellojpa.ex.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class Ex_JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

//            Ex_Member member1 = new Ex_Member();
//            member1.setUsername("hello1");
//            em.persist(member1);
//
//            Ex_Member member2 = new Ex_Member();
//            member1.setUsername("hello1");
//            em.persist(member2);
//
//            Ex_Member member3 = new Ex_Member();
//            member1.setUsername("hello1");
//            em.persist(member3);
//
//
//
//            em.flush();
//            em.clear();
//
//            Ex_Member m1 = em.find(Ex_Member.class, member1.getId());
//            // member 출력
//            System.out.println(m1.getClass());
//
//
//            Ex_Member ref = em.getReference(Ex_Member.class, member1.getId());
//            // member 출력됨 (이미 영속성컨텍스트에 올라가있기 때문)
//            System.out.println(ref.getClass());
//
//            // true (둘다 getReference 여도 true)
//            System.out.println( (m1 == ref) );
//
//
//
//
//
//            Ex_Member refMember = em.getReference(Ex_Member.class, member2.getId());
//            // 프록시 출력
//            System.out.println(refMember.getClass());
//
//
//            Ex_Member findMember = em.find(Ex_Member.class, member2.getId());
//            // 프록시 출력 (둘이 true임을 맞추기위해)
//            System.out.println(findMember.getClass());
//
//            // true
//            System.out.println( (refMember == findMember) );
//
//
//
//
//
//            Ex_Member refMember2 = em.getReference(Ex_Member.class, member3.getId());
//
//            System.out.println(refMember2.getClass());
//
//            em.detach(refMember2);
//            //em.close();
//            //em.clear();
//
//            // 영속성컨텍스트에서 가져와야하는데 detach 또는 close 로 에러 발생
//            refMember2.getUsername();
//
//            // 초기화를 하면 true 안하면 false
//            System.out.println(emf.getPersistenceUnitUtil().isLoaded(refMember2));
//
//
//            // 강제 초기화
//            Hibernate.initialize(refMember2);





            // x
            //System.out.println(m1.getClass() == m2.getClass());

//
//            //Ex_Member findMember = em.find(Ex_Member.class, member.getId());
//            Ex_Member findMember = em.getReference(Ex_Member.class, member.getId());
//
//            System.out.println(findMember.getClass());
//            System.out.println(findMember.getId());
//
//            // 두번호출해도 더 이상 조회안함
//            System.out.println(findMember.getUsername());
//
//            Ex_Team team = new Ex_Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Ex_Member member1 = new Ex_Member();
//            member1.setUsername("member1");
//            member1.setTeam(team);
//
//            em.persist(member1);
//
//            em.flush();
//            em.clear();
//
//            Ex_Member m = em.find(Ex_Member.class, member1.getId());
//
//            // find를헀지만 Team은 프록시 출력됨!
//            System.out.println(m.getTeam().getClass());
//
//            System.out.println("---");
//            // 값을 가져옴
//            member1.getTeam().getName();
//            System.out.println("---");
//
//
//            // 쿼리가 2개 나감 member, team
//            List<Ex_Member> members = em.createQuery("select m from Ex_Member  m", Ex_Member.class).getResultList();


            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);


            // 3개를 persist 해야함.. cascade 를 추가하면 하나만 해도 됌
            //
            em.persist(parent);
            //em.persist(child1);
            //em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            //findParent.getChildList().remove(0);

            em.remove(findParent);

//
//
//            Ex_Member member3 = new Ex_Member();
//            member3.setUsername("3");
//            member3.setHomeAddress(new Ex_Address("city", "street", "zipcode"));
//            member3.setWorkPeriod(new Ex_Period());
//
//            em.persist(member3);
//
//
//            // 업데이트가 두번 나간다
//            Ex_Address address = new Ex_Address("city", "street", "10000");
//
//            Ex_Member member1 = new Ex_Member();
//            member1.setUsername("1m");
//            member1.setHomeAddress(address);
//            em.persist(member1);
//
//
//            Ex_Member member2 = new Ex_Member();
//            member2.setUsername("2m");
//            member2.setHomeAddress(address);
//            em.persist(member1);


            //member1.getHomeAddress().setCity("newCity");


//                       // 복사한 값을 써야함!
//            Ex_Address copyAddress = new Ex_Address(address.getCity(), address.getStreet(), address.getZipcode());


            Ex_Member memberK = new Ex_Member();
            memberK.setUsername("memberK");
            memberK.setHomeAddress(new Ex_Address("city", "street", "zip"));

            memberK.getFavoriteFoods().add("치킨");
            memberK.getFavoriteFoods().add("족발");
            memberK.getFavoriteFoods().add("피자");

            memberK.getAddressHistory().add(new Ex_AddressEntity("old1", "street", "zip"));
            memberK.getAddressHistory().add(new Ex_AddressEntity("old2", "street", "zip"));


            em.persist(memberK);

            em.flush();
            em.clear();

            Ex_Member findMember = em.find(Ex_Member.class, memberK.getId());

//            // 값 타입 컬렉션 조회
//            List<Ex_Address> addressHistory = findMember.getAddressHistory();
//
//            // 이때 실제 조회
//            for(Ex_Address f_Address : addressHistory){
//                System.out.println(f_Address.getCity());
//            }


            // 값 타입 수정
            // setCity XXX  통으로 갈아끼우는게 맞음
            findMember.setHomeAddress(new Ex_Address("new","street","zip"));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //
            //findMember.getAddressHistory().remove(new Ex_Address("old1", "street", "zip"));
            //findMember.getAddressHistory().add(new Ex_Address("newCity1", "street", "zip"));



            List<Ex_Member> memberList = em.createQuery(
                    "select m from Ex_Member m where m.username like '%kim' ",
                    Ex_Member.class)
                    .getResultList();


            // Criteria 사용 예시
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Ex_Member> query = cb.createQuery(Ex_Member.class);

            Root<Ex_Member> m = query.from(Ex_Member.class);

            CriteriaQuery<Ex_Member> cq = query.select(m).where(cb.equal((m.get("username")), "kim"));

            List<Ex_Member> resultList = em.createQuery(cq).getResultList();

            // 네이티브 쿼리
            em.createNativeQuery("select MEMBER_ID, city, street from EX_MEMBER").getResultList();

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }

    private static void printMember(Ex_Member member) {
        System.out.println("member = " + member.getUsername());
    }

    private static void printMemberAndTeam(Ex_Member member){
        String username = member.getUsername();
        System.out.println("username: "+ username);

        Ex_Team team = member.getTeam();
        System.out.println("team =" + team);
    }
}
