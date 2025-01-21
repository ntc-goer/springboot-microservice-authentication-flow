package ntcgoer.authservice.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequestDto {
    @NotEmpty
    @JsonProperty("userNameOrEmail")
    private String userNameOrEmail;
    @NotEmpty
    @JsonProperty("password")
    private String password;
}
