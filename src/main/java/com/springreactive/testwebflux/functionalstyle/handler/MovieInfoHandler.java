package com.springreactive.testwebflux.functionalstyle.handler;

import com.springreactive.testwebflux.client.TestRestClient;
import com.springreactive.testwebflux.domain.MovieInfo;
import com.springreactive.testwebflux.model.DummyModelInfo;
import com.springreactive.testwebflux.model.DummyModelResponse;
import com.springreactive.testwebflux.repository.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovieInfoHandler {

    private MovieInfoRepository movieInfoRepository;

    @Autowired
    TestRestClient testRestClient;

    public MovieInfoHandler(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<ServerResponse> insertMovie(ServerRequest request) {
        return request.bodyToMono(MovieInfo.class)
                .flatMap(movieInfoRepository::save)
                .flatMap(movieInfo -> ServerResponse.ok().bodyValue(movieInfo));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll();
        return ServerResponse.ok().body(movieInfoFlux, MovieInfo.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return request.bodyToMono(MovieInfo.class)
                .flatMap(movieInfoRequest -> movieInfoRepository.findById(movieInfoRequest.getMovieInfoId())
                        .flatMap(movieInfo -> ServerResponse.ok().bodyValue(movieInfo)));
    }

    public Mono<ServerResponse> findByYear(ServerRequest request) {
        return request.bodyToMono(MovieInfo.class)
                .map(movieInfoRequest -> movieInfoRepository.findByYear(movieInfoRequest.getYear()))
                .flatMap(movieInfoByYear -> ServerResponse.ok().body(movieInfoByYear, MovieInfo.class));
    }

    public Mono<ServerResponse> updateMovie(ServerRequest request) {
        return request.bodyToMono(MovieInfo.class)
                .flatMap(movieInfoRequest -> movieInfoRepository.findById(movieInfoRequest.getMovieInfoId())
                        .map(movieInfoById -> {
                            movieInfoById.setName(movieInfoRequest.getName() + (int) (Math.random() * 100));
                            return movieInfoById;
                        })
                        .flatMap(movieInfoRepository::save)
                        .flatMap(savedMovieInfo -> ServerResponse.ok().bodyValue(savedMovieInfo)));

    }

    public Mono<ServerResponse> deleteMovie(ServerRequest request) {
        return request.bodyToMono(MovieInfo.class)
                .flatMap(movieInfoRequest -> movieInfoRepository.deleteById(movieInfoRequest.getMovieInfoId())
                        .then(ServerResponse.noContent().build()));
    }

    public Mono<ServerResponse> testMonoCallDummyService(ServerRequest request) {
        return ServerResponse.ok().body(testRestClient.testMonoCallDummyService(), DummyModelResponse.class);
    }

    public Mono<ServerResponse> testFluxCallDummyService(ServerRequest request) {
        return ServerResponse.ok().body(testRestClient.testFluxCallDummyService(), DummyModelInfo.class);
    }
}
