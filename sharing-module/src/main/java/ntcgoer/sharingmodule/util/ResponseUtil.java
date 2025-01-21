package ntcgoer.sharingmodule.util;

import ntcgoer.sharingmodule.exception.advice.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public static <T> ResponseEntity<HttpResponse<T>> Ok(T data) {
        HttpResponse<T> resp = new HttpResponse<>();
        resp.setStatus(200);
        resp.setError(null);
        resp.setTimestamp(LocalDateTime.now());
        resp.setData(data);
        return ResponseEntity.ok(resp);
    }

    public static  ResponseEntity<HttpResponse<Object>> Ok() {
        HttpResponse<Object> resp = new HttpResponse<>();
        resp.setStatus(200);
        resp.setError(null);
        resp.setTimestamp(LocalDateTime.now());
        resp.setData(null);
        return ResponseEntity.ok(resp);
    }

    public static ResponseEntity<HttpResponse<?>> InternalError(String message) {
        HttpResponse<String> resp = new HttpResponse<>();
        resp.setStatus(500);
        resp.setError(HttpStatus.INTERNAL_SERVER_ERROR);
        resp.setMessage(message);
        resp.setTimestamp(LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp);
    }

    public static ResponseEntity<HttpResponse<?>> BadRequest(String message) {
        HttpResponse<String> resp = new HttpResponse<>();
        resp.setStatus(400);
        resp.setError(HttpStatus.BAD_REQUEST);
        resp.setMessage(message);
        resp.setTimestamp(LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp);
    }

    public static ResponseEntity<HttpResponse<?>> NotFound(String message) {
        HttpResponse<String> resp = new HttpResponse<>();
        resp.setStatus(404);
        resp.setError(HttpStatus.NOT_FOUND);
        resp.setMessage(message);
        resp.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    public static ResponseEntity<HttpResponse<?>> Unauthorized(String message) {
        HttpResponse<String> resp = new HttpResponse<>();
        resp.setStatus(401);
        resp.setError(HttpStatus.UNAUTHORIZED);
        resp.setMessage(message);
        resp.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    public static ResponseEntity<HttpResponse<?>> Forbidden(String message) {
        HttpResponse<String> resp = new HttpResponse<>();
        resp.setStatus(403);
        resp.setError(HttpStatus.FORBIDDEN);
        resp.setMessage(message);
        resp.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
    }

    public static ResponseEntity<HttpResponse<?>> Conflict(String message) {
        HttpResponse<String> resp = new HttpResponse<>();
        resp.setStatus(409);
        resp.setError(HttpStatus.CONFLICT);
        resp.setMessage(message);
        resp.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
    }

    public static <T> ResponseEntity<HttpResponse<T>> Created(T data) {
        HttpResponse<T> resp = new HttpResponse<>();
        resp.setStatus(204);
        resp.setError(null);
        resp.setTimestamp(LocalDateTime.now());
        resp.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

}
