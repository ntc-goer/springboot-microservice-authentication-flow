package ntcgoer.gatewayservice.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RouteSetting {
    private String url;
    private boolean authenticated;
}
