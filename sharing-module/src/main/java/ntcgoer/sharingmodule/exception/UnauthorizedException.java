package ntcgoer.sharingmodule.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
