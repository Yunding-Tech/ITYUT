package fun.ruafafa.ityut.exception;

public class NoAvailableNodeException extends RuntimeException{
    public NoAvailableNodeException() {
        super();
    }

    public NoAvailableNodeException(String message) {
        super(message);
    }

    public NoAvailableNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableNodeException(Throwable cause) {
        super(cause);
    }
}
