package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        final Member savedMember = memberJpaRepository.save(member);
        final Member findMember = memberJpaRepository.find(savedMember.getId());

        assertEquals(savedMember.getId(), findMember.getId());
        assertEquals(savedMember.getUsername(), findMember.getUsername());
        assertEquals(findMember, member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

        List<Member> all = memberJpaRepository.findAll();
        assertEquals(all.size(), 2);

        long count = memberJpaRepository.count();
        assertEquals(count, 2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertEquals(deletedCount, 0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("AAA", 20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertEquals(result.get(0).getUsername(), "AAA");
        assertEquals(result.get(0).getAge(), 20);
        assertEquals(result.size(), 1);

    }

    @Test
    public void testNamedQuery() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsername("AAA");
        assertEquals(result.get(0).getUsername(), "AAA");
        assertEquals(result.get(0).getAge(), 10);
        assertEquals(result.size(), 1);
    }

    @Test
    public void paging() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        assertEquals(members.size(), 3);
        assertEquals(totalCount, 5);
    }

    @Test
    public void bulkUpdate() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 40));

        final int resultCount = memberJpaRepository.bulkAgePlus(20);
        assertEquals(resultCount, 3);

    }


}