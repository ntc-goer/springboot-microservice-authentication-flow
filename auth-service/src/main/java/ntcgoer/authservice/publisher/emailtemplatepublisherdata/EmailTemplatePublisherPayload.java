package ntcgoer.authservice.publisher.emailtemplatepublisherdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailTemplatePublisherPayload {
    @JsonProperty("metadata")
    private EmailTemplatePublisherMetadata metadata;
    @JsonProperty("variable")
    private Map<String,String> variable;
}
