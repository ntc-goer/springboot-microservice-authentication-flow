package ntcgoer.authservice.client.feignclient.feignclientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateSignupDataClientRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("password")
    private String password;
}
