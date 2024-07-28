package com.springreactive.testwebflux.functionalstyle.handler;

import com.springreactive.testwebflux.client.TestRestClient;
import com.springreactive.testwebflux.domain.MovieInfo;
import com.springreactive.testwebflux.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovieInfoHandler extends BaseHandler {

    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    TestRestClient testRestClient;

    public Mono<ServerResponse> insertMovie(ServerRequest request) {
//        return request.bodyToMono(MovieInfo.class)
//                .flatMap(movieInfoService::insertMovie)
//                .flatMap(movieInfo -> ServerResponse.ok().bodyValue(movieInfo));
        return apiResponseSuccess(request.bodyToMono(MovieInfo.class).flatMap(movieInfoService::insertMovie));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
//        return request.bodyToMono(MovieInfo.class)
//                .flatMap(movieInfoRequest -> movieInfoService.findById(movieInfoRequest.getMovieInfoId())
//                        .flatMap(movieInfo -> ServerResponse.ok().bodyValue(movieInfo)));
        return apiResponseSuccess(request.bodyToMono(MovieInfo.class)
                .flatMap(movieInfoRequest -> movieInfoService.findById(movieInfoRequest.getMovieInfoId())));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<MovieInfo> movieInfoFlux = movieInfoService.findAll();
//        return ServerResponse.ok().body(movieInfoFlux, MovieInfo.class);
        return apiFluxResponseSuccess(movieInfoFlux, null);
    }

    public Mono<ServerResponse> findAllPack(ServerRequest request) {
        Flux<MovieInfo> movieInfoFlux = movieInfoService.findAll();
//        return ServerResponse.ok().body(movieInfoFlux, MovieInfo.class);
        return apiResponseSuccess(movieInfoFlux);
    }

    public Mono<ServerResponse> findByYear(ServerRequest request) {
//        return request.bodyToMono(MovieInfo.class)
//                .map(movieInfoRequest -> movieInfoService.findByYear(movieInfoRequest.getYear()))
//                .flatMap(movieInfoByYear -> ServerResponse.ok().body(movieInfoByYear, MovieInfo.class));
        return request.bodyToMono(MovieInfo.class)
                .map(movieInfoRequest -> movieInfoService.findByYear(movieInfoRequest.getYear()))
                .flatMap(movieInfoByYear -> apiFluxResponseSuccess(movieInfoByYear, null));

//        return apiResponseSuccess(request.bodyToMono(MovieInfo.class)
//                .map(movieInfoRequest -> movieInfoService.findByYear(movieInfoRequest.getYear())));
    }

    public Mono<ServerResponse> findByYearPack(ServerRequest request) {
//        return request.bodyToMono(MovieInfo.class)
//                .map(movieInfoRequest -> movieInfoService.findByYear(movieInfoRequest.getYear()))
//                .flatMap(movieInfoByYear -> ServerResponse.ok().body(movieInfoByYear, MovieInfo.class));
        return request.bodyToMono(MovieInfo.class)
                .map(movieInfoRequest -> movieInfoService.findByYear(movieInfoRequest.getYear()))
                .flatMap(this::apiResponseSuccess);

//        return apiResponseSuccess(request.bodyToMono(MovieInfo.class)
//                .map(movieInfoRequest -> movieInfoService.findByYear(movieInfoRequest.getYear())));
    }


    public Mono<ServerResponse> updateMovie(ServerRequest request) {
//        return request.bodyToMono(MovieInfo.class)
//                .flatMap(movieInfoRequest -> movieInfoService.findById(movieInfoRequest.getMovieInfoId())
//                        .map(movieInfoById -> {
//                            movieInfoById.setName(movieInfoRequest.getName() + (int) (Math.random() * 100));
//                            return movieInfoById;
//                        })
//                        .flatMap(movieInfoService::update)
//                        .flatMap(savedMovieInfo -> ServerResponse.ok().bodyValue(savedMovieInfo)));
        return apiResponseSuccess(request.bodyToMono(MovieInfo.class)
                .flatMap(movieInfoRequest -> movieInfoService.findById(movieInfoRequest.getMovieInfoId())
                        .map(movieInfoById -> {
                            movieInfoById.setName(movieInfoRequest.getName() + (int) (Math.random() * 100));
                            return movieInfoById;
                        })
                        .flatMap(movieInfoService::update)));

    }

    public Mono<ServerResponse> deleteMovie(ServerRequest request) {
//        return request.bodyToMono(MovieInfo.class)
//                .flatMap(movieInfoRequest -> movieInfoService.delete(movieInfoRequest.getMovieInfoId())
//                        .then(ServerResponse.noContent().build()));
        return apiResponseSuccess(request.bodyToMono(MovieInfo.class)
                .flatMap(movieInfoRequest -> movieInfoService.delete(movieInfoRequest.getMovieInfoId())));
    }

    public Mono<ServerResponse> testMonoCallDummyService(ServerRequest request) {
//        return ServerResponse.ok().body(testRestClient.testMonoCallDummyService(), DummyModelResponse.class);
        return apiResponseSuccess(testRestClient.testMonoCallDummyService());
    }

    public Mono<ServerResponse> testFluxCallDummyService(ServerRequest request) {
//        return ServerResponse.ok().body(testRestClient.testFluxCallDummyService(), DummyModelInfo.class);
        return apiFluxResponseSuccess(testRestClient.testFluxCallDummyService(), null);
    }

    public Mono<ServerResponse> testFluxCallDummyServicePack(ServerRequest request) {
//        return ServerResponse.ok().body(testRestClient.testFluxCallDummyService(), DummyModelInfo.class);
        return apiResponseSuccess(testRestClient.testFluxCallDummyService());
    }
}
