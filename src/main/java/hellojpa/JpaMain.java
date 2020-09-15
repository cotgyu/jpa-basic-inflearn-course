package hellojpa;

import hellojpa.domain.Member;
import hellojpa.ex.Ex_Member;
import hellojpa.ex.Ex_Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 저장
            Ex_Team team = new Ex_Team();
            team.setName("TeamA");
            em.persist(team);


            Ex_Member member = new Ex_Member();
            member.setUsername("member1");
            //member.setTeamId(team.getId());
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Ex_Member findMember = em.find(Ex_Member.class, member.getId());

//            Long findTeamId = findMember.getTeamId();
//            Ex_Team findTeam = em.find(Ex_Team.class, findTeamId);

//            Ex_Team findTeam = findMember.getTeam();
//            System.out.println("find Team: " + findTeam.getName());

            List<Ex_Member> members = findMember.getTeam().getMembers();

            for(Ex_Member m : members){
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
