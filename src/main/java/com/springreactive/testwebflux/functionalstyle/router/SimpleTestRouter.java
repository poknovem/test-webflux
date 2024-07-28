package com.springreactive.testwebflux.functionalstyle.router;

import com.springreactive.testwebflux.functionalstyle.handler.MovieInfoHandler;
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
public class SimpleTestRouter {

    private MovieInfoHandler movieInfoHandler;

    public SimpleTestRouter(MovieInfoHandler movieInfoHandler) {
        this.movieInfoHandler = movieInfoHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> router() {
        return route()
                .POST("/functional/mono", request -> Mono.just(1).flatMap(result -> ServerResponse.ok().bodyValue(result)))
                .POST("/functional/flux", request -> ServerResponse.ok().body(Flux.just(1, 2, 3), Integer.class))
                .POST("/functional/stream", request -> ServerResponse.ok()
                        .contentType(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE)).body(Flux.interval(Duration.ofSeconds(1)), Integer.class))
                .POST("/functional/get-movie", request -> movieInfoHandler.findById(request))
                .POST("/functional/insert-movie", request -> movieInfoHandler.insertMovie(request))
                .POST("/functional/get-movies", request -> movieInfoHandler.findAll(request))
                .POST("/functional/get-movies-by-year", request -> movieInfoHandler.findByYear(request))
                .POST("/functional/update-movie", request -> movieInfoHandler.updateMovie(request))
                .POST("/functional/delete-movie", request -> movieInfoHandler.deleteMovie(request))
                .GET("/functional/call-dummy-service-mono", request -> movieInfoHandler.testMonoCallDummyService(request))
                .GET("/functional/call-dummy-service-flux", request -> movieInfoHandler.testFluxCallDummyService(request))
                .build();
    }
}
