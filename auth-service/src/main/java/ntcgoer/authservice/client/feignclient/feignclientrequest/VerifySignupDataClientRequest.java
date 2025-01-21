package ntcgoer.authservice.client.feignclient.feignclientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class VerifySignupDataClientRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("userName")
    private String userName;
}
