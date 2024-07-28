package com.springreactive.testwebflux.controller;

import com.springreactive.testwebflux.model.APIResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BaseController {

    public <T> Mono<APIResponse<T>> apiResponseSuccess (Mono<T> mono){
        return mono.map(APIResponse::success).log();
    }

//    public <T> Mono<APIResponse<T>> apiResponseSuccess (Flux<T> flux){
//        return flux.flatMap(s -> APIResponse.success(s)).log();
//    }

    public <T> Flux<APIResponse<T>> apiFluxResponseSuccess (Flux<T> flux){
        return flux.map(APIResponse::success).log();
    }
}
