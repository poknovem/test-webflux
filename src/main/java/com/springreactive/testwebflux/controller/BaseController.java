package com.springreactive.testwebflux.controller;

import com.springreactive.testwebflux.model.APIResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BaseController {

    public <T> Mono<APIResponse<T>> apiResponseSuccess(Mono<T> mono) {
        return mono.map(APIResponse::success).defaultIfEmpty(APIResponse.defaultz()).log();
    }

    public <T> Mono<APIResponse<List<T>>> apiResponseSuccess(Flux<T> flux) {
        return flux.collectList().map(APIResponse::success).defaultIfEmpty(APIResponse.defaultz()).log();
    }

    public <T> Flux<APIResponse<T>> apiFluxResponseSuccess(Flux<T> flux) {
        return flux.map(APIResponse::success).defaultIfEmpty(APIResponse.defaultz()).log();
    }
}
