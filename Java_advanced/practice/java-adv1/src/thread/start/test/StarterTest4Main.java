package thread.start.test;
import static util.MyLogger.log;

public class StarterTest4Main {

    public static void main(String[] args) {
        Thread threadA = new Thread(new MyPrinter("A", 1000), "Thread-A");
        Thread threadB = new Thread(new MyPrinter("B", 500), "Thread-B");
        threadA.start();
        threadB.start();
    }

    static class MyPrinter implements Runnable {

        private final String str;
        private final int ms;

        MyPrinter(String str, int ms) {
            this.str = str;
            this.ms = ms;
        }

        @Override
        public void run() {
            while (true) {
                log(str);
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
