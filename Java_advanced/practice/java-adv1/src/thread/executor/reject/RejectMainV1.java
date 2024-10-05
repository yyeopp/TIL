package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.*;
import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

import static util.ThreadUtils.sleep;
public class RejectMainV1 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new ThreadPoolExecutor.AbortPolicy());
        executor.submit(new RunnableTask("task1"));
        try {
            executor.submit(new RunnableTask("task2"));
        } catch (RejectedExecutionException e) {
            log("요청 초과");
            log(e);
        }
        executor.close();

    }
}
