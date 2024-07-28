package com.springreactive.testwebflux.functionalstyle.router;

import com.springreactive.testwebflux.model.APIResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BaseRouter {
    public <T> Mono<ServerResponse> apiResponseSuccess (Mono<T> mono){
        return ServerResponse
                .ok()
                .body(mono.map(APIResponse::success).log(), APIResponse.class);
    }

    public <T> Mono<ServerResponse> apiFluxResponseSuccess (Flux<T> flux, MediaType mediaType){
        return ServerResponse
                .ok()
                .contentType(mediaType)
                .body(flux.map(APIResponse::success).log(), APIResponse.class);
    }
}
