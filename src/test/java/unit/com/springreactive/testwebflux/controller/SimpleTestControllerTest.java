package com.springreactive.testwebflux.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.Objects;

@WebFluxTest(controllers = SimpleTestController.class)
@AutoConfigureWebTestClient
class SimpleTestControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void flux() {
        webTestClient.post().uri("/simpleTestController/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void flux_approach2() {
        var flux = webTestClient.post().uri("/simpleTestController/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    void flux_approach3() {
        var flux = webTestClient.post().uri("/simpleTestController/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                        . consumeWith(listEntityExchangeResult -> {
                            var responseBody = listEntityExchangeResult.getResponseBody();
                            assert (Objects.requireNonNull(responseBody).size() == 3);
                        });
    }

    @Test
    void mono() {
        var flux = webTestClient.post().uri("/simpleTestController/mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Integer.class)
                . consumeWith(integerEntityExchangeResult -> {
                    var responseBody = integerEntityExchangeResult.getResponseBody();
                    assert (Objects.requireNonNull(responseBody) == 1);
                });
    }

    @Test
    void stream() {
        var flux = webTestClient.post().uri("/simpleTestController/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNext(0L, 1L, 2L)
                .thenCancel()
                .verify();
    }
}