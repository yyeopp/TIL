package thread.executor.future;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class FutureExceptionMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000);
        try {
            log("future.get() 호출 시도, future.state() : " + future.state());
            Integer result = future.get();
            log("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            log("e = " + e);
            Throwable cause = e.getCause();
            log("cause = " + cause);
        }
        es.close();
    }

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() throws IllegalStateException {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }

}
