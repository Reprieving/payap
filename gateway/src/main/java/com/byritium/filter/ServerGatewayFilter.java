package com.byritium.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by  on 2020/8/1.
 */
@Component
public class ServerGatewayFilter implements GatewayFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ServerGatewayFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        String url = serverWebExchange.getRequest().getURI().getPath();
        HttpHeaders oldHeader = serverWebExchange.getRequest().getHeaders();

        //向headers中放文件，记得build
        ServerHttpRequest host = serverWebExchange.getRequest().mutate().header("uid", "1").build();
        //将现在的request 变成 change对象
        ServerWebExchange build = serverWebExchange.mutate().request(host).build();

        return gatewayFilterChain.filter(build);
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
