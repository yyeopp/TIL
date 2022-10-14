package com.hello.spring.service;

import com.hello.spring.domain.Member;
import com.hello.spring.repository.MemberRepository;
import com.hello.spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("helloSpring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        Member member1 = new Member();
        member1.setName("springTest");

        Member member2 = new Member();
        member2.setName("springTest");

        memberService.join(member1);
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertEquals(e.getMessage(), "이미 존재하는 회원입니다");
//        }

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals(e.getMessage(), "이미 존재하는 회원입니다");

    }

}