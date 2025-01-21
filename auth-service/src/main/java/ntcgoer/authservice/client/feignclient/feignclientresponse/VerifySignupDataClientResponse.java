package ntcgoer.authservice.client.feignclient.feignclientresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifySignupDataClientResponse {
    @JsonProperty("userNameTaken")
    private Boolean userNameTaken;
    @JsonProperty("emailTaken")
    private Boolean emailTaken;
}
