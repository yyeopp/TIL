package hello.proxy.pureproxy.concreteproxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic {
    private final ConcreteLogic concreteLogic;

    @Override
    public String operation() {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();
        String result = concreteLogic.operation();
        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 / result = {} ms", resultTime);
        return result;
    }
}
