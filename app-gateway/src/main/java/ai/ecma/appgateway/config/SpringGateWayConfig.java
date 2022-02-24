/* Created by IntelliJ IDEA.
 User: Mirzaahmatov Aziz
Date: 5/18/2021
Time: 10:41 AM
 To change this template use File | Settings | File Templates.
*/
package ai.ecma.appgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringGateWayConfig {
    @Bean
    public RouteLocator gateWayRotes(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(r -> r.path("/api/auth/**")
                        .uri("lb://AUTH-SERVICE")//security
                )
                .route(r -> r.path("/api/academic/**")
                        .uri("http://localhost:8082/")//academic servise
                )
                .route(r -> r.path("/api/account/**")
                        .uri("http://localhost:8083/")//account servise
                )
                .build();
    }
}
