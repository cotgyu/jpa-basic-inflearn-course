package hellojpa;

import hellojpa.domain.Book;
import hellojpa.jpql.J_Member;
import hellojpa.jpql.J_MemberDTO;
import hellojpa.jpql.J_MemberType;
import hellojpa.jpql.J_Team;

import javax.persistence.*;
import java.util.List;

public class J_JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            J_Member j_member = new J_Member();
            j_member.setUsername("member1");
            j_member.setAge(10);
            em.persist(j_member);

            TypedQuery<J_Member> query1 = em.createQuery("select m  from J_Member m", J_Member.class);
            Query query2 = em.createQuery("select m.username, m.age  from J_Member m");


            List<J_Member> resultList = query1.getResultList();

            // 결과 값이 하나 일 때
            // getSingleResult();

            // 보통 엮어서 사용
            J_Member singleResult = em.createQuery("select m  from J_Member m where m.username = :username", J_Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println(singleResult.getUsername());

            em.flush();
            em.clear();

            // 프로젝션 설명
            List<J_Member> resultList1 = em.createQuery("select m  from J_Member m", J_Member.class)
                    .getResultList();

            J_Member findMember = resultList1.get(0);
            findMember.setAge(20);


            // 여러 값 new 명령어로 조회
            List<J_MemberDTO> resultList2 = em.createQuery("select new hellojpa.jpql.J_MemberDTO(m.username, m.age) from J_Member m", J_MemberDTO.class)
                    .getResultList();

            J_MemberDTO j_memberDTO = resultList2.get(0);

            System.out.println(j_memberDTO.getUsername());
            System.out.println(j_memberDTO.getAge());


            // 페이징
            List<J_Member> resultList3 = em.createQuery("select m from J_Member m order by m.age desc", J_Member.class)
                    .setFirstResult(2)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println(resultList3.size());




            // 조인
            J_Team j_team = new J_Team();
            j_team.setName("team1");
            em.persist(j_team);

            J_Member j_member2 = new J_Member();
            j_member2.setUsername("member3");
            j_member2.setAge(10);
            j_member2 .setJ_team(j_team);

            em.persist(j_member2);

            em.flush();
            em.clear();


            String query = "select m from J_Member m inner join m.j_team t";

            List<J_Member> resultList4 = em.createQuery(query, J_Member.class).getResultList();



            String typeQuery = "select m.username, 'HELLO', TRUE from J_Member m " +
                    "where m.j_type = hellojpa.jpql.J_MemberType.ADMIN" ;

            em.createQuery(typeQuery).getResultList();


            // 파라미터 사용
            String typeQuery2 = "select m.username, 'HELLO', TRUE from J_Member m " +
                    "where m.j_type = :userType" ;

            em.createQuery(typeQuery2)
                    .setParameter("userType", J_MemberType.ADMIN)
                    .getResultList();


            // 조건식
            String caseQuery =
                    "select " +
                        "case when m.age <= 10 then '학생요금' " +
                            "when m.age >= 60 then '경로요금' "+
                            "else '일반요금' " +
                            "end " +
                    "from J_Member m";


            List<String> resultList5 = em.createQuery(caseQuery, String.class)
                    .getResultList();


            // COALESCE
            String COALESCEQuery =
                    "select coalesce(m.username, '이름없는 회원') from J_Member m";

            List<String> resultList6 = em.createQuery(COALESCEQuery, String.class)
                    .getResultList();


            // NULLIF
            String NULLIFQuery =
                    "select nullif(m.username, '관리자') from J_Member m";

            List<String> resultList7 = em.createQuery(NULLIFQuery, String.class)
                    .getResultList();



            // 기본함수
            String testQuery =
                    "select concat('a', 'b') from J_Member m";

            em.createQuery(testQuery, String.class)
                    .getResultList();



            String sizeQuery =
                    "select size(t.members) from J_Team t";

            em.createQuery(sizeQuery, String.class)
                    .getResultList();




            String gcQuery =
                    "select function('group_concat', m.username) from J_Member m";

            em.createQuery(gcQuery, String.class)
                    .getResultList();


            // fetch join
            J_Team teamA = new J_Team();
            teamA.setName("팀 A");
            em.persist(teamA);

            J_Team teamB = new J_Team();
            teamA.setName("팀 B");
            em.persist(teamB);

            J_Member member1 = new J_Member();
            member1.setUsername("회원1");
            member1.setJ_team(teamA);
            em.persist(member1);


            J_Member member2 = new J_Member();
            member2.setUsername("회원2");
            member2.setJ_team(teamA);
            em.persist(member2);

            J_Member member3 = new J_Member();
            member3.setUsername("회원3");
            member3.setJ_team(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            // 새로운 팀을 조회할 때 마다 SQL이 새로 나감
            String fetchQuery1 = "select m from J_Member m";

            List<J_Member> resultList8 = em.createQuery(fetchQuery1, J_Member.class)
                    .getResultList();


            // 한번에 모든 팀 조회
            String fetchQuery2 = "select m from J_Member m join fetch m.j_team";

            List<J_Member> resultList9 = em.createQuery(fetchQuery2, J_Member.class)
                    .getResultList();

            for (J_Member member : resultList9) {

                System.out.println("member = : "+ member.getUsername()+ ", " + member.getJ_team());

            }

            // named
            em.createNamedQuery("J_Member.findByUsername", J_Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();


            // 벌크 연산
            int resultCount = em.createQuery("update J_Member m set m.age = 20")
                    .executeUpdate();


            J_Member findMember2 = em.find(J_Member.class, member1.getId());

            // update 전 age가 출력됨. 벌크연산 후 em.clear(); 를 해줄 것!
            System.out.println(findMember2.getAge());



            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
