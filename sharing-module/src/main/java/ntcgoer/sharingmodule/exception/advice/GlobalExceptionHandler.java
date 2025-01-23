package ntcgoer.sharingmodule.exception.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import ntcgoer.sharingmodule.exception.*;
import ntcgoer.sharingmodule.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private void logException(Exception ex) {
        // Get the stack trace element to retrieve method and class where the exception occurred
        StackTraceElement stackTraceElement = ex.getStackTrace()[0];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        String message = String.format("%s.%s: %s",
                className, methodName, ex.getMessage());

        logger.error(message, ex);
    }

    @ExceptionHandler({InternalServerErrorException.class, JsonProcessingException.class})
    public ResponseEntity<HttpResponse<?>> handleRuntimeException(RuntimeException exception) {
        logException(exception);
        return ResponseUtil.InternalError(exception.getMessage());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<HttpResponse<?>> handleBadRequestException(BadRequestException e) {
        logException(e);
        return ResponseUtil.BadRequest(e.getMessage());
    }

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<HttpResponse<?>> handleConflictException(ConflictException e) {
        logException(e);
        return ResponseUtil.Conflict(e.getMessage());
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<HttpResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
        logException(e);
        return ResponseUtil.NotFound(e.getMessage());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<HttpResponse<?>> handleUnauthorizedException(UnauthorizedException e) {
        logException(e);
        return ResponseUtil.Unauthorized(e.getMessage());
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<HttpResponse<?>> handleForbiddenException(ForbiddenException e) {
        logException(e);
        return ResponseUtil.Forbidden(e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<HttpResponse<?>> handleValidatorException(MethodArgumentNotValidException e) {
        logException(e);
        String customException = e.getBindingResult().getFieldErrors().stream()
                .map(x -> String.format("%s: %s", x.getField(), x.getDefaultMessage()))
                .collect(Collectors.joining(","));
        return ResponseUtil.BadRequest(customException);
    }

    @ExceptionHandler(value = {FeignException.class})
    public ResponseEntity<HttpResponse<?>> handleFeignClientException(FeignException e) {
        logException(e);
        return ResponseUtil.BadRequest(e.getMessage());
    }
}
