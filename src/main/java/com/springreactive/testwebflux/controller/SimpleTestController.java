package com.springreactive.testwebflux.controller;


import com.springreactive.testwebflux.client.TestRestClient;
import com.springreactive.testwebflux.domain.MovieInfo;
import com.springreactive.testwebflux.model.APIResponse;
import com.springreactive.testwebflux.model.DummyModelInfo;
import com.springreactive.testwebflux.model.DummyModelResponse;
import com.springreactive.testwebflux.service.MovieInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/simpleTestController")
public class SimpleTestController extends BaseController {

    @Autowired
    MovieInfoService movieInfoService;

    @Autowired
    TestRestClient testRestClient;

    Sinks.Many<MovieInfo> movieInfoSink = Sinks.many().replay().all();

    @PostMapping("/mono")
    public Mono<APIResponse<Integer>> mono() {
        return apiResponseSuccess(Mono.just(1));
    }

    @PostMapping("/flux")
    public Flux<APIResponse<Integer>> flux() {
        return apiFluxResponseSuccess(Flux.just(1, 2, 3));
    }

    @PostMapping("/flux-pack")
    public Mono<APIResponse<List<Integer>>> fluxPack() {
        return apiResponseSuccess(Flux.just(1, 2, 3));
    }

    // Server Sent Events (SSE)
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<APIResponse<Long>> stream() {
        return apiFluxResponseSuccess(Flux.interval(Duration.ofSeconds(1)));
    }

    // Server Sent Events (SSE)
    @PostMapping(value = "/stream-pack", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<APIResponse<List<Long>>> streamPack() {
        return apiResponseSuccess(Flux.interval(Duration.ofSeconds(1)));
    }

    @PostMapping(value = "/insert-movie")
    public Mono<APIResponse<MovieInfo>> insertMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return apiResponseSuccess(movieInfoService.insertMovie(movieInfo));
    }

    @PostMapping(value = "/get-movies-pack")
    public Mono<APIResponse<List<MovieInfo>>> getMoviesPack() {
        return apiResponseSuccess(movieInfoService.findAll());
    }

    @PostMapping(value = "/get-movies")
    public Flux<APIResponse<MovieInfo>> getMovies() {
        return apiFluxResponseSuccess(movieInfoService.findAll());
    }

    @PostMapping(value = "/get-movies-by-year")
    public Flux<APIResponse<MovieInfo>> getMoviesByYear(@RequestBody MovieInfo movieInfo) {
        return apiFluxResponseSuccess(movieInfoService.findByYear(movieInfo.getYear()));
    }

    @PostMapping(value = "/get-movies-by-year-pack")
    public Mono<APIResponse<List<MovieInfo>>> getMoviesByYearPack(@RequestBody MovieInfo movieInfo) {
        return apiResponseSuccess(movieInfoService.findByYear(movieInfo.getYear()));
    }

    // Server Sent Events (SSE)
    @PostMapping(value = "/get-movies-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<APIResponse<MovieInfo>> getMoviesStream() {
        return apiFluxResponseSuccess(movieInfoSink.asFlux());
    }

    // Server Sent Events (SSE)
//    @GetMapping(value = "/get-movies-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
//    public Flux<MovieInfo> getMoviesStream() {
//        return movieInfoSink.asFlux();
//    }

    @PostMapping(value = "/get-movie")
    public Mono<APIResponse<MovieInfo>> getMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return apiResponseSuccess(movieInfoService.findById(movieInfo.getMovieInfoId())
                .doOnNext(movieInfoResult -> movieInfoSink.tryEmitNext(movieInfoResult)));
    }

    @PostMapping(value = "/update-movie")
    public Mono<APIResponse<MovieInfo>> updateMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return apiResponseSuccess(movieInfoService.update(movieInfo));
    }

    @PostMapping(value = "/delete-movie")
    public Mono<APIResponse<Void>> deleteMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return apiResponseSuccess(movieInfoService.delete(movieInfo.getMovieInfoId()));
    }

    @GetMapping(value = "/call-dummy-service-mono")
    public Mono<APIResponse<DummyModelResponse>> testCallDummyServiceMono() {
        Mono<DummyModelResponse> responseMono = testRestClient.testMonoCallDummyService();
        responseMono.doOnNext(response -> {
            System.out.println("response doOnNext >>> " + response);
        });
        responseMono.map(response -> {
            System.out.println("response map >>> " + response);
            return response;
        }).log();
        return apiResponseSuccess(responseMono);
    }

    @GetMapping(value = "/call-dummy-service-flux")
    public Flux<APIResponse<DummyModelInfo>> testCallDummyServiceFlux() {
        Flux<DummyModelInfo> responseFlux = testRestClient.testFluxCallDummyService();
        responseFlux.map(response -> {
            System.out.println("response >>> " + response);
            return response;
        });
        return apiFluxResponseSuccess(responseFlux);
    }

    @GetMapping(value = "/call-dummy-service-flux-pack")
    public Mono<APIResponse<List<DummyModelInfo>>> testCallDummyServiceFluxPack() {
        Flux<DummyModelInfo> responseFlux = testRestClient.testFluxCallDummyService();
        responseFlux.map(response -> {
            System.out.println("response >>> " + response);
            return response;
        });
        return apiResponseSuccess(responseFlux);
    }
}
