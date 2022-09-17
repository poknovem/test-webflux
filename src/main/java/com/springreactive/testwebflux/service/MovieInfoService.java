package com.springreactive.testwebflux.service;

import com.springreactive.testwebflux.domain.MovieInfo;
import com.springreactive.testwebflux.repository.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    public Mono<MovieInfo> insertMovie(MovieInfo movieInfo){
        return movieInfoRepository.save(movieInfo);
    }

    public Mono<MovieInfo> findById(String movieInfoId){
        return movieInfoRepository.findById(movieInfoId);
    }

    public Flux<MovieInfo> findAll(){
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> update(MovieInfo movieInfo){
        return movieInfoRepository
                .findById(movieInfo.getMovieInfoId())
                .flatMap(updateMovieInfo -> {
            updateMovieInfo.setCast(movieInfo.getCast());
            updateMovieInfo.setName(movieInfo.getName());
            updateMovieInfo.setRelease_date(movieInfo.getRelease_date());
            updateMovieInfo.setYear(movieInfo.getYear());
            return movieInfoRepository.save(updateMovieInfo);
        });
    }

    public Mono<Void> delete(String movieInfoId){
        return movieInfoRepository.deleteById(movieInfoId);
    }
}
