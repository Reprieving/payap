package com.byritium.config;


import com.byritium.filter.ServerGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by  on 2020/8/1.
 */
@Configuration
public class GatewayRoutesConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r ->
                        r.path("/transaction/**")
                                .filters(
                                        f -> f.stripPrefix(1)
                                                .filters(new ServerGatewayFilter())
                                )
                                .uri("lb://transaction")
                )
                .build();
    }
}
