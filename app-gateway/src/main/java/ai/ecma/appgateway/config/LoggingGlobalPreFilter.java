package ai.ecma.appgateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingGlobalPreFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        ServerHttpRequest modifiedRequest1 = exchange.getRequest().mutate().
                                build();
                    }));
    }
}
