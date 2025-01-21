package ntcgoer.userservice.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateAccountVerifiedAtRequestDto {
    @NotEmpty
    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("verifiedAt")
    private LocalDateTime verifiedAt;
}
