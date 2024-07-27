package com.springreactive.testwebflux.repository;

import com.springreactive.testwebflux.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {

    // Connect to DB or API must return Flux or Mono for use Non-blocking I/O benefits
    Flux<MovieInfo> findByYear(Integer year);
}
