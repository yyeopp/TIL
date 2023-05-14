package com.hello.core.autowired;

import com.hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {
        @Autowired(required = false)        // Member가 null이면 호출 자체를 막을 수 있음
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);

        }

        @Autowired(required = true)
        public void setNoBean2(@Nullable Member noBean2) {      // Member가 null이여도 호출 가능
            System.out.println("noBean2 = " + noBean2);

        }

        @Autowired(required = true)
        public void setNoBean3(Optional<Member> noBean3) {      // 마찬가지로 Member가 null
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
