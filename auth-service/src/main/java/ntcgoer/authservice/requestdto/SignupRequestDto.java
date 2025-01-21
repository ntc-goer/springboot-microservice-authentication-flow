package ntcgoer.authservice.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotEmpty
    @JsonProperty(value = "email")
    private String email;

    @NotEmpty
    @Size(min = 6, max = 16)
    @JsonProperty(value = "userName")
    private String userName;

    @NotEmpty
    @Size(min = 8)
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter.")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit.")
    @JsonProperty(value = "password")
    private String password;
}
