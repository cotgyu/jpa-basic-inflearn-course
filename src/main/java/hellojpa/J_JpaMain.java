package hellojpa;

import hellojpa.domain.Book;
import hellojpa.jpql.J_Member;

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


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
