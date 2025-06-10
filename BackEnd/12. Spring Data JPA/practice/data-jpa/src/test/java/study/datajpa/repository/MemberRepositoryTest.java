package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember() {
        Member member = new Member("memberB");
        final Member savedMember = memberRepository.save(member);
        final Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertEquals(savedMember.getId(), findMember.getId());
        assertEquals(savedMember.getUsername(), findMember.getUsername());
        assertEquals(findMember, member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

        List<Member> all = memberRepository.findAll();
        assertEquals(all.size(), 2);

        long count = memberRepository.count();
        assertEquals(count, 2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertEquals(deletedCount, 0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertEquals(result.get(0).getUsername(), "AAA");
        assertEquals(result.get(0).getAge(), 20);
        assertEquals(result.size(), 1);

    }

    @Test
    public void testNamedQuery() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");
        assertEquals(result.get(0).getUsername(), "AAA");
        assertEquals(result.get(0).getAge(), 10);
        assertEquals(result.size(), 1);
    }

    @Test
    public void testQuery() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.findUser("AAA", 10);
        assertEquals(result.get(0), m1);
    }

    @Test
    public void findUsernameList() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);
        List<String> result = memberRepository.findUsernameList();
        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void findMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 10, team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println(dto.toString());
        }
    }

    @Test
    public void findByNames() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void returnType() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        final List<Member> list = memberRepository.findListByUsername("AAA");               // 조회결과 없을 시, Empty List
        final Member member = memberRepository.findMemberByUsername("AAA");                 // 조회결과 없을 시, NULL (JPA 표준에서는 NoResultException)
        final Optional<Member> optional = memberRepository.findOptionalByUsername("AAA");   // Java 8 이후 표준

    }

    @Test
    public void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        final PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> members = page.getContent();
        long totalElements = page.getTotalElements();
        assertEquals(members.size(), 3);
        assertEquals(totalElements, 5);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getTotalPages(), 2);
        assertTrue(page.isFirst());
        assertTrue(page.hasNext());

        final Page<MemberDto> memberDto = page.map(member -> new MemberDto(member.getId(), member.getUsername(), "teamA"));

    }

    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        final int resultCount = memberRepository.bulkAgePlus(20);
        assertEquals(resultCount, 3);
//        em.flush();
//        em.clear();

        final List<Member> result = memberRepository.findByUsername("member5");
        final Member member5 = result.get(0);
        assertEquals(member5.getAge(), 41);
    }

    @Test
    public void findMemberLazy() {
        final Team teamA = new Team("teamA");
        final Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member1", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        final List<Member> members = memberRepository.findMemberEntityGraphByUsername("member1");
        for (Member member : members) {
            System.out.println(member.getUsername());
            System.out.println(member.getTeam().getName());
        }
    }

    @Test
    public void queryHint() {
        final Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        final Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
        em.flush();
    }

    @Test
    public void lock() {
        final Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        List<Member> findMember = memberRepository.findLockByUsername("member1");

    }

    @Test
    public void callCustom() {
        final List<Member> memberCustom = memberRepository.findMemberCustom();
        System.out.println(memberCustom.toString());
    }

    @Test
    public void queryByExample() {
        final Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member2", 10, teamA);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        Team t = new Team("teamA");
        Member m = new Member("member1", 10, t);

        final ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("age");

        Example<Member> example = Example.of(m, matcher);
        final List<Member> result = memberRepository.findAll(example);

    }


    @Test
    public void nativeQuery() {
        final Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member2", 10, teamA);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();
        final Member result = memberRepository.findByNativeQuery("member1");
        System.out.println(result);
    }

    @Test
    public void nativeQueryProjections() {
        final Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member2", 10, teamA);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();
        final Page<MemberProjections> result = memberRepository.findByNativeProjection(PageRequest.of(0, 10));
        final List<MemberProjections> content = result.getContent();
        for (MemberProjections memberProjections : content) {
            System.out.println(memberProjections.getUsername());
            System.out.println(memberProjections.getTeamName());
        }
    }
}