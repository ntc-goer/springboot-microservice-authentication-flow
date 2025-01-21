package ntcgoer.sharingmodule.configuration;

import lombok.Getter;
import lombok.Setter;
import ntcgoer.sharingmodule.util.JwtUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "np-jwt")
public class JwtConfiguration {
    private String secretKey;

    @Bean
    public JwtUtil getJwtUtil(){
        return new JwtUtil(secretKey);
    }
}
