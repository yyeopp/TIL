package thread.bounded;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class ProducerTask implements Runnable{

    private BoundedQueue queue;
    private String request;

    public ProducerTask(BoundedQueue queue, String request) {
        this.queue = queue;
        this.request = request;
    }

    @Override
    public void run() {
        log("[생산 시도] : " + request + " -> " + queue);
        queue.put(request);
        log("[생산 완료] : " + request + " -> " + queue);
    }
}
