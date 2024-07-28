package com.springreactive.testwebflux.client;

import com.springreactive.testwebflux.model.DummyModelInfo;
import com.springreactive.testwebflux.model.DummyModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TestRestClient {

    @Autowired
    private WebClient webClient;

    public Mono<DummyModelResponse> testMonoCallDummyService(){
        return webClient.get()
                .uri("https://dummyjson.com/posts")
                .retrieve()
                .bodyToMono(DummyModelResponse.class)
                .log();
    }

    public Flux<DummyModelInfo> testFluxCallDummyService(){
        return webClient.get()
                .uri("https://dummyjson.com/posts")
                .retrieve()
                .bodyToFlux(DummyModelInfo.class)
                .log();
    }

}
