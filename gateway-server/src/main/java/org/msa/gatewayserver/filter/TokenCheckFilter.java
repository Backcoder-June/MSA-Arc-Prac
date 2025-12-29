package org.msa.gatewayserver.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenCheckFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        boolean success = false;

        Object tmep = request.getHeaders().get("token");
        Object tmep2 = request.getHeaders().get("accountId");
        String token = "";
        String accountId = "";
        if (tmep != null) {
            token = tmep.toString().replace("[", "").replace("]", "");
        }
        if (tmep2 != null) {
            accountId = tmep2.toString().replace("[", "").replace("]", "");
        }

        log.info("filtered token = {}", token);

        if(!success){
            return errorResponse(exchange);

        }

        return chain.filter(exchange);
    }


    private Mono<Void> errorResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        Gson gson = new Gson();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        Map<String,String> map = new HashMap<>();
        map.put("code", "401");
        map.put("message", "Unauthorized TOKEN!");

        String json = gson.toJson(map);

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.writeWith(Mono.just(buffer));
    }
}
