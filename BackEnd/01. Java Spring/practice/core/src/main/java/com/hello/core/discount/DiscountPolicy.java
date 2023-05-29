package com.hello.core.discount;

import com.hello.core.member.Member;
import org.springframework.stereotype.Component;

public interface DiscountPolicy {

    /**
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);


}
