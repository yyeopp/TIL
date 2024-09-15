package thread.start.test;

import static util.MyLogger.log;

public class StarterTest3Main {
    public static void main(String[] args) {

        Thread counter = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                log("value: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "counter");
        counter.start();
    }

}
