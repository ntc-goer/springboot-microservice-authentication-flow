package ntcgoer.authservice.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateSignupDataResponse {
    @JsonProperty("email")
    private String email;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("isVerified")
    private Boolean isVerified;
}