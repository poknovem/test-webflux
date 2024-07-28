package com.springreactive.testwebflux.client;

import com.springreactive.testwebflux.model.DummyModelInfo;
import com.springreactive.testwebflux.model.DummyModelResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TestRestClient extends BaseRestClient {

    public Mono<DummyModelResponse> testMonoCallDummyService() {
//        return webClient.get()
//                .uri("https://dummyjson.com/posts")
//                .retrieve()
//                .bodyToMono(DummyModelResponse.class)
//                .log();

        return getMono("https://dummyjson.com/posts", DummyModelResponse.class);
    }

    public Flux<DummyModelInfo> testFluxCallDummyService() {
//        return webClient.get()
//                .uri("https://dummyjson.com/posts")
//                .retrieve()
//                .bodyToFlux(DummyModelInfo.class)
//                .log();
        return getFlux("https://dummyjson.com/posts", DummyModelInfo.class);
    }

}
