package thread.executor.future;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class FutureCancelMain {

    private static boolean mayInterruptIfRunning = true;
//    private static boolean mayInterruptIfRunning = false;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        log("Future.state : " + future.state());
        sleep(3000);
        log("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        log("Future.state : " + future.state());
        log("cancel result = " + cancelResult);
        try {
            log("Future result : " + future.get());
        } catch (CancellationException e) {
            log("Future는 이미 취소됨");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        es.close();
    }

    static class MyTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            try {
                for (int i = 0; i < 10; i++) {
                    log("작업 중 : " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log("인터럽트 발생");
                return "Interrupted";
            }
            return "Completed";
        }
    }

}
