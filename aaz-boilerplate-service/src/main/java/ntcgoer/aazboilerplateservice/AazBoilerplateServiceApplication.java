package ntcgoer.aazboilerplateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ntcgoer.aazboilerplateservice", "ntcgoer.sharingmodule"})
@EnableConfigurationProperties
public class AazBoilerplateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AazBoilerplateServiceApplication.class, args);
    }

}
