package hello.jdbc.repository.ex;

public class MyDuplicationKeyException extends MyDbException {
    public MyDuplicationKeyException() {
    }

    public MyDuplicationKeyException(String message) {
        super(message);
    }

    public MyDuplicationKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicationKeyException(Throwable cause) {
        super(cause);
    }
}
