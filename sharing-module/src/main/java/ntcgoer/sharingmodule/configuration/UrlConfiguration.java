package ntcgoer.sharingmodule.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "np-url")
public class UrlConfiguration {
    private String gatewayUrl;
}
