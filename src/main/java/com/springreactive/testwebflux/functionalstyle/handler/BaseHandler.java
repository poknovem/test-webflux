package com.springreactive.testwebflux.functionalstyle.handler;

import com.springreactive.testwebflux.model.APIResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BaseHandler {
    public <T> Mono<ServerResponse> apiResponseSuccess(Mono<T> mono) {
        return ServerResponse
                .ok()
                .body(mono.map(APIResponse::success).log(), APIResponse.class);
    }

    public <T> Mono<ServerResponse> apiResponseSuccess(Flux<T> flux) {
        return ServerResponse
                .ok()
                .body(flux.collectList().map(APIResponse::success).log(), APIResponse.class);
    }

    public <T> Mono<ServerResponse> apiFluxResponseSuccess(Flux<T> flux, MediaType mediaType) {
        return ServerResponse
                .ok()
                .contentType(mediaType)
                .body(flux.map(APIResponse::success).log(), APIResponse.class);
    }
}
