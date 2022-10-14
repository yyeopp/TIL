package com.hello.spring;

import com.hello.spring.repository.JdbcMemberRepository;
import com.hello.spring.repository.JdbcTemplateMemberRepository;
import com.hello.spring.repository.MemberRepository;
import com.hello.spring.repository.MemoryMemberRepository;
import com.hello.spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
