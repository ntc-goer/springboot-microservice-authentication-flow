package ntcgoer.userservice.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class VerifySignupDataRequestDto {
    @NotEmpty
    @JsonProperty(value = "email")
    private String email;
    @NotEmpty
    @Size(min = 6, max = 16)
    @JsonProperty(value = "userName")
    private String userName;
}
