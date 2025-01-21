package ntcgoer.authservice.publisher.emailtemplatepublisherdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailTemplatePublisherMetadata {
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("template")
    private String template;
    @JsonProperty("emailTo")
    private String emailTo;
}
