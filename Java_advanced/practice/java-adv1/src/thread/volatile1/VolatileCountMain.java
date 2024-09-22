package thread.volatile1;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class VolatileCountMain {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        t.start();
        sleep(1000);
        task.flag = false;
        log("flag = " + task.flag + ", count = " + task.count + " in main()");
    }

    static class MyTask implements Runnable {
        volatile boolean flag = true;
        volatile long count;

        @Override
        public void run() {
            while (flag) {
                count++;
                if (count % 100_000_000 == 0) {
                    log("flag = " + flag + ", count = " + count);
                }
            }
            log("flag = " + flag + ", count = " + count + " 종료");
        }
    }
}
