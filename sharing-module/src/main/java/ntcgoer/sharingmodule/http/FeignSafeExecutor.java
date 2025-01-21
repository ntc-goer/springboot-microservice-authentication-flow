package ntcgoer.sharingmodule.http;

import ntcgoer.sharingmodule.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignSafeExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FeignSafeExecutor.class);

    public <R> InternalFeignCallResponse<R> Call(HttpFunction<R> f) {
        String errorCode = null;
        String errorMessage = null;
        R response = null;
        try {
            response = f.trigger();
        } catch (BadRequestException ex) {
            logger.error(String.format("FeignSafeExecutor.call.FeignException: %s", ex.getMessage()));
            errorMessage = ex.getMessage();
            errorCode = ErrorCode.BAD_REQUEST;
        } catch (UnauthorizedException ex) {
            logger.error(String.format("FeignSafeExecutor.call.UnauthorizedException: %s", ex.getMessage()));
            errorMessage = ex.getMessage();
            errorCode = ErrorCode.UNAUTHORIZED;
        } catch (ForbiddenException ex) {
            logger.error(String.format("FeignSafeExecutor.call.ForbiddenException: %s", ex.getMessage()));
            errorMessage = ex.getMessage();
            errorCode = ErrorCode.FORBIDDEN;
        } catch (ResourceNotFoundException ex) {
            logger.error(String.format("FeignSafeExecutor.call.ResourceNotFoundException: %s", ex.getMessage()));
            errorMessage = ex.getMessage();
            errorCode = ErrorCode.NOT_FOUND;
        } catch (ConflictException ex) {
            logger.error(String.format("FeignSafeExecutor.call.ConflictException: %s", ex.getMessage()));
            errorMessage = ex.getMessage();
            errorCode = ErrorCode.CONFLICT;
        } catch (Exception ex) {
            logger.error(String.format("FeignSafeExecutor.call.Exception: %s", ex.getMessage()));
            errorMessage = ex.getMessage();
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
        return InternalFeignCallResponse.<R>builder().
                error(errorCode).
                errorMessage(errorMessage).
                response(response).
                build();
    }
}
