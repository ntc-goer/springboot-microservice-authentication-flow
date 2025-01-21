package ntcgoer.sharingmodule.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}
