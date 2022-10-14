package com.hello.spring;

import com.hello.spring.repository.*;
import com.hello.spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    private final EntityManager entityManager;

    private final MemberRepository memberRepository;

    public SpringConfig(DataSource dataSource, EntityManager entityManager, MemberRepository memberRepository) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new SpringDataJpaMemberRepository(memberRepository);
//    }
}
