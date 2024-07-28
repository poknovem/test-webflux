package com.springreactive.testwebflux.functionalstyle.router;

import com.springreactive.testwebflux.functionalstyle.handler.MovieInfoHandler;
import com.springreactive.testwebflux.model.APIResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class SimpleTestRouter extends BaseRouter{

    private MovieInfoHandler movieInfoHandler;

    public SimpleTestRouter(MovieInfoHandler movieInfoHandler) {
        this.movieInfoHandler = movieInfoHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> router() {
        return route()
                .POST("/functional/mono", request -> apiResponseSuccess(Mono.just(1)))
                .POST("/functional/flux", request ->  apiFluxResponseSuccess(Flux.just(1, 2, 3), null))
                .POST("/functional/stream", request -> apiFluxResponseSuccess(Flux.interval(Duration.ofSeconds(1))
                        , MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE)))
                .POST("/functional/get-movie", request -> movieInfoHandler.findById(request))
                .POST("/functional/insert-movie", request -> movieInfoHandler.insertMovie(request))
                .POST("/functional/get-movies", request -> movieInfoHandler.findAll(request))
                .POST("/functional/get-movies-pack", request -> movieInfoHandler.findAllPack(request))
                .POST("/functional/get-movies-by-year", request -> movieInfoHandler.findByYear(request))
                .POST("/functional/get-movies-by-year-pack", request -> movieInfoHandler.findByYearPack(request))
                .POST("/functional/update-movie", request -> movieInfoHandler.updateMovie(request))
                .POST("/functional/delete-movie", request -> movieInfoHandler.deleteMovie(request))
                .GET("/functional/call-dummy-service-mono", request -> movieInfoHandler.testMonoCallDummyService(request))
                .GET("/functional/call-dummy-service-flux", request -> movieInfoHandler.testFluxCallDummyService(request))
                .GET("/functional/call-dummy-service-flux-pack", request -> movieInfoHandler.testFluxCallDummyServicePack(request))
                .build();
    }
}
