package ntcgoer.sharingmodule.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
