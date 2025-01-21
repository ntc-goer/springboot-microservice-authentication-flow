package ntcgoer.authservice.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SigninResponseDto {
    @JsonProperty("accessToken")
    private String accessToken;
}
