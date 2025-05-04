package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jpabook.jpashop.domain.Member;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

//            Member member = new Member();
//            member.setName("111");
//            Order order = new Order();
//            order.setMember(member);
//
//            em.persist(member);
//            em.persist(order);
//
//            em.flush();
//            em.clear();
//
////            Order o = em.find(Order.class, order.getId());
////            System.out.println(o.getClass());
////            System.out.println(o.getMember().getName());
//
//            List<Order> orders = em.createQuery("select o from Order o", Order.class).getResultList();

//            Child child1 = new Child();
//            Child child2 = new Child();
//
//            Parent parent = new Parent();
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent);
////            em.persist(child1);
////            em.persist(child2);
//            em.flush();
//            em.clear();
//
//            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildren().remove(0);

            List<Member> result = em.createQuery("select m from Member m where m.name like '%%'", Member.class).getResultList();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
            Root<Member> m = query.from(Member.class);
            query.select(m).where(cb.equal(m.get("name"), "kim"));
            List<Member> result2 = em.createQuery(query).getResultList();

            List<Member> result3 = em.createNativeQuery("select * from member").getResultList();

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
