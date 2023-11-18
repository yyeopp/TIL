package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(service::callThrow)
                .isInstanceOf(MyCheckedException.class);
    }

    static class MyCheckedException extends Exception {
        /**
         * Exception 을 상속받은 예외는 체크 예외가 된다.
         * @param message
         */
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 잡아서 처리하거나, 던지거나 둘 중 하나를 해야 컴파일 가능하다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외처리, message = {}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던지는코드
         * 예외를 밖으로 던지려면, throws 예외 구문을 메서드에 선언해야 함
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }

    }

    static class Repository {
        public void call () throws MyCheckedException{
            throw new MyCheckedException("ex");
        }
    }
}
