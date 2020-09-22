package hellojpa;

import hellojpa.domain.Member;
import hellojpa.ex.Ex_Member;
import hellojpa.ex.Ex_Movie;
import hellojpa.ex.Ex_Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Ex_JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Ex_Movie ex_movie = new Ex_Movie();
            ex_movie.setDirector("a");
            ex_movie.setActor("bbb");
            ex_movie.setName("영화");
            ex_movie.setPrice(100000);

            em.persist(ex_movie);

            em.flush();
            em.clear();


            Ex_Movie findMovie = em.find(Ex_Movie.class, ex_movie.getId());

            System.out.println(findMovie);



            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
