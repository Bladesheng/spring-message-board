package messageboard.exceptions;

public class EmptyMessageEditException extends RuntimeException {
    public EmptyMessageEditException(String message) {
        super(message);
    }
}

