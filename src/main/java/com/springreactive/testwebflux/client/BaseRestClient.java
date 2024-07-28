package com.springreactive.testwebflux.client;

import com.springreactive.testwebflux.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BaseRestClient {
    @Autowired
    private WebClient webClient;

    public <T> Mono<T> getMono(String uri, Class<T> clazz) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::handleError)
                .bodyToMono(clazz)
                .log();
    }

    public <T> Mono<T> postMono(String uri, Class<T> clazz) {
        return webClient.post()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::handleError)
                .bodyToMono(clazz)
                .log();
    }

    public <T> Flux<T> getFlux(String uri, Class<T> clazz) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::handleError)
                .bodyToFlux(clazz)
                .log();
    }

    public <T> Flux<T> postFlux(String uri, Class<T> clazz) {
        return webClient.post()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::handleError)
                .bodyToFlux(clazz)
                .log();
    }

    private Mono<Exception> handleError(ClientResponse clientResponse) {
        if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.error(new CustomException(
                    "There is no services", clientResponse.statusCode().value()));
        }
        return clientResponse.bodyToMono(String.class)
                .flatMap(responseMessage -> Mono.error(new CustomException("Error ja", clientResponse.statusCode().value())));
    }
}
