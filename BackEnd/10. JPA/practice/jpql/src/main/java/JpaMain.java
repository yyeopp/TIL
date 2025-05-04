import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.Member;
import jpql.Team;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("TEAM_A");
            em.persist(team);

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("강성엽" + i);
                member.setAge(i);
                member.setTeam(team);
                em.persist(member);
            }


//
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            Query query2 = em.createQuery("select m.username, m.age from Member m");
//
//            Member result = em.createQuery("select m from Member m where username = :username", Member.class)
//                    .setParameter("username", "강성엽")
//                    .getSingleResult();

            em.flush();
            em.clear();
//
//            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//            Member findMember = result.get(0);
//            findMember.setAge(30);
//
//
//            List<Address> result2 = em.createQuery("select o.address from Order o", Address.class).getResultList();

            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            String query = "select m from Member m order by m.age desc";



            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
