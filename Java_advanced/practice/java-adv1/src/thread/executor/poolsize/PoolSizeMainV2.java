package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class PoolSizeMainV2 {
    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);
        log("pool 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }
        es.close();
        printState(es);
        log("pool 종료");
    }
}
