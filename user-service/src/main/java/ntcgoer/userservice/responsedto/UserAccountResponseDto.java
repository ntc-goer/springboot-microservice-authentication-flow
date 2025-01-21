package ntcgoer.userservice.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserAccountResponseDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("lastLoginAt")
    private LocalDateTime lastLoginAt;
    @JsonProperty("verifiedAt")
    private LocalDateTime verifiedAt;

}
