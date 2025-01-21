package ntcgoer.sharingmodule.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
    public InternalServerErrorException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
