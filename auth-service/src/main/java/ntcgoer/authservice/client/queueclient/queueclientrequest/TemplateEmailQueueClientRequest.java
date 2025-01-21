package ntcgoer.authservice.client.queueclient.queueclientrequest;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class TemplateEmailQueueClientRequest {
    private String toEmail;
    private String subject;
    private String template;
    private Map<String, String> data;
}
