package ntcgoer.authservice.client.feignclient.feignclientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVerifiedAtClientRequest {
    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("verifiedAt")
    private LocalDateTime verifiedAt;
}
