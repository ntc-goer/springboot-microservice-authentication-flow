package ntcgoer.sharingmodule.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
    public ConflictException() {
        super(ErrorCode.CONFLICT);
    }
}
