package ntcgoer.sharingmodule.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "np-queue.template-email")
public class TemplateEmailQueueConfiguration {
    private String emailTopicExchangeName;
    private String emailTemplateQueueName;
    private String emailTemplateRoutingKey;
}
