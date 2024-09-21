package thread.control.yield;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class YieldMain {
     static final int THREAD_COUNT = 1000;

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                // 그대로 두기      // 1.
//                 sleep(1);        // 2. 스레드를 인위적으로 멈추기 (TIMED_WAITING)
                 Thread.yield();  // 3. 스레드를 스케줄링 큐에 넣기 (RUNNABLE)

            }
        }
    }
}
