package ntcgoer.sharingmodule.exception.advice;

import feign.FeignException;
import ntcgoer.sharingmodule.exception.*;
import ntcgoer.sharingmodule.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<HttpResponse<?>> handleRuntimeException(RuntimeException exception) {
        return ResponseUtil.InternalError(exception.getMessage());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<HttpResponse<?>> handleBadRequestException(BadRequestException e) {
        return ResponseUtil.BadRequest(e.getMessage());
    }

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<HttpResponse<?>> handleConflictException(ConflictException e) {
        return ResponseUtil.Conflict(e.getMessage());
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<HttpResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseUtil.NotFound(e.getMessage());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<HttpResponse<?>> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseUtil.Unauthorized(e.getMessage());
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<HttpResponse<?>> handleForbiddenException(ForbiddenException e) {
        return ResponseUtil.Forbidden(e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<HttpResponse<?>> handleValidatorException(MethodArgumentNotValidException e) {
        String customException = e.getBindingResult().getFieldErrors().stream()
                .map(x -> String.format("%s: %s", x.getField(), x.getDefaultMessage()))
                .collect(Collectors.joining(","));
        return ResponseUtil.BadRequest(customException);
    }

    @ExceptionHandler(value = {FeignException.class})
    public ResponseEntity<HttpResponse<?>> handleFeignClientException(FeignException e) {
        System.out.println(e.getMessage());
        return ResponseUtil.BadRequest(e.getMessage());
    }
}
