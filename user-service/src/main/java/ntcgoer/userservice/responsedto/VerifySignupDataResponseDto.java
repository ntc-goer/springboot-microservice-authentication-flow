package ntcgoer.userservice.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class VerifySignupDataResponseDto {
    @JsonProperty("userNameTaken")
    private Boolean usernameTaken;
    @JsonProperty("emailTaken")
    private Boolean emailTaken;
}
