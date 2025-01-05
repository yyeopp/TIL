package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV1Test {
    @Autowired
    CallServiceV1 callService;

    @Test
    void external() {
        callService.external();
    }

}