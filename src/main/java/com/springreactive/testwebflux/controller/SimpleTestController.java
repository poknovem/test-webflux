package com.springreactive.testwebflux.controller;


import com.springreactive.testwebflux.domain.MovieInfo;
import com.springreactive.testwebflux.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/simpleTestController")
public class SimpleTestController {

    @Autowired
    MovieInfoService movieInfoService;

    Sinks.Many<MovieInfo> movieInfoSink = Sinks.many().replay().all();

    @PostMapping("/mono")
    public Mono<Integer> mono() {
        return Mono.just(1).log();
    }

    @PostMapping("/flux")
    public Flux<Integer> flux() {
        return Flux.just(1, 2, 3).log();
    }

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> stream() {
        return Flux.interval(Duration.ofSeconds(1)).log();
    }

    @PostMapping(value = "/insert-movie")
    public Mono<MovieInfo> insertMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return movieInfoService.insertMovie(movieInfo).log();
    }

    @PostMapping(value = "/get-movies")
    public Flux<MovieInfo> getMovies() {
        return movieInfoService.findAll().log();
    }

    @PostMapping(value = "/get-movies-by-year")
    public Flux<MovieInfo> getMoviesByYear(@RequestBody MovieInfo movieInfo) {
        return movieInfoService.findByYear(movieInfo.getYear()).log();
    }

    @PostMapping(value = "/get-movies-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieInfo> getMoviesStream() {
        return movieInfoSink.asFlux();
    }

    @PostMapping(value = "/get-movie")
    public Mono<MovieInfo> getMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return movieInfoService.findById(movieInfo.getMovieInfoId()).doOnNext(movieInfoResult -> movieInfoSink.tryEmitNext(movieInfoResult)).log();
    }

    @PostMapping(value = "/update-movie")
    public Mono<MovieInfo> updateMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return movieInfoService.update(movieInfo).log();
    }

    @PostMapping(value = "/delete-movie")
    public Mono<Void> deleteMovie(@RequestBody @Validated MovieInfo movieInfo) {
        return movieInfoService.delete(movieInfo.getMovieInfoId()).log();
    }
}
