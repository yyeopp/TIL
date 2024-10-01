package thread.collection.simple.list;

import static util.MyLogger.log;

public class SimpleListsMainV2 {
    public static void main(String[] args) throws InterruptedException {

//        test(new BasicList());
//        test(new SyncList());
        test(new SyncProxyList(new BasicList()));

    }

    private static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());
        Runnable addA = () -> {
            list.add("A");
            log("Thread-1 : list.add(A)");
        };

        Runnable addB = () -> {
            list.add("B");
            log("Thread-2 : list.add(B)");
        };

        Thread t1 = new Thread(addA, "Thread-1");
        Thread t2 = new Thread(addB, "Thread-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log(list);
    }
}
