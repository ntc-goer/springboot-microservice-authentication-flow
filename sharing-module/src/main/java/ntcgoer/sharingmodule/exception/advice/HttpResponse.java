package ntcgoer.sharingmodule.exception.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResponse<T> {
    @JsonProperty(value = "timestamp")
    private LocalDateTime timestamp;
    @JsonProperty(value = "status")
    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private HttpStatus error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "message")
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "data")
    private T data;
}
