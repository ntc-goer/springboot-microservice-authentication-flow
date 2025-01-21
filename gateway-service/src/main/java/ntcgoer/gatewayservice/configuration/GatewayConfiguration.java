package ntcgoer.gatewayservice.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    AuthenticationFilter authenticationFilter;

    @Bean
    RouteLocator routes(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
        RouteLocatorBuilder.Builder routes = builder.routes();
        buildRouteDocs(routes,
                "userservice",
                "authservice");
        buildRouteToService(routes, "authservice",
                new RouteSetting("v1/hello-world", true),
                new RouteSetting("v1/signup", false),
                new RouteSetting("v1/verify-email", false),
                new RouteSetting("v1/signin", false)
        );
        return routes.build();
    }

    void buildRouteToService(RouteLocatorBuilder.Builder routes, String serviceName, RouteSetting... routeSettings) {
        for (RouteSetting routeSetting : routeSettings) {
            routes.route(p -> p.path(String.format("/%s", routeSetting.getUrl()))
                    .filters(f -> {
                        if (routeSetting.isAuthenticated()) {
                            f.filter(this.authenticationFilter);
                        }
                        return f;
                    })
                    .uri(String.format("lb://%s", serviceName)));
        }
    }

    void buildRouteDocs(RouteLocatorBuilder.Builder routes, String... serviceNames) {
        for (String serviceName : serviceNames) {
            routes.route(p -> p.path(
                            String.format("/%s/api-docs", serviceName),
                            String.format("/%s/api-docs/swagger-config", serviceName),
                            String.format("/%s/swagger-ui.html", serviceName),
                            String.format("/%s/swagger-ui/**", serviceName)
                    )
                    .uri(String.format("lb://%s", serviceName)));
        }
    }
}
