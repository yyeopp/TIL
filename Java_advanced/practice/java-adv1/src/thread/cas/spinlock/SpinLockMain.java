package thread.cas.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class SpinLockMain {
    public static void main(String[] args) {
//        SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();
        Runnable runnable = () -> {
            spinLock.lock();
            try {
                log("비즈니스 로직 실행");
            } finally {
                spinLock.unlock();
            }
        };

        Thread t1 = new Thread(runnable, "Thread-1");
        Thread t2 = new Thread(runnable, "Thread-2");

        t1.start();
        t2.start();
    }
}
