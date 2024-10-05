package thread.executor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ExecutorShutdownMain {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000));

        printState(es);
        log("== shutdown 시작");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료");
        printState(es);

    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown();
        try {
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                List<Runnable> tasks = es.shutdownNow();
                log(tasks.toString());
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException e) {
            es.shutdownNow();
        }
    }
}
