package com.hello.spring.repository;

import com.hello.spring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager en;

    public JpaMemberRepository(EntityManager en) {
        this.en = en;
    }

    @Override
    public Member save(Member member) {
        en.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = en.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = en.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return en.createQuery("select m from Member m", Member.class).getResultList();
    }
}
