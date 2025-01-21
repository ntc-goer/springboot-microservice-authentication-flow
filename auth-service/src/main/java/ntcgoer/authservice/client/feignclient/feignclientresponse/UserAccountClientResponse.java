package ntcgoer.authservice.client.feignclient.feignclientresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserAccountClientResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("lastLoginAt")
    private LocalDateTime lastLoginAt;
    @JsonProperty("verifiedAt")
    private LocalDateTime verifiedAt;
}
