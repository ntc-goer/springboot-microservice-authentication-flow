package ntcgoer.gatewayservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    private final RedisTemplate<Object, Object> redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Extract Authorization header
        String authToken = exchange.getRequest().getHeaders().getFirst("X-NP-Authentication-Token");
        if(redisTemplate == null){
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
        String redisKey = String.format("auth-token:%s:data", authToken);
        // Validate the token
        if (authToken == null || Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // Continue the filter chain
        return chain.filter(exchange);
    }
}
