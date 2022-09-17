package com.springreactive.testwebflux.repository;

import com.springreactive.testwebflux.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepository  extends ReactiveMongoRepository<MovieInfo, String> {

}
