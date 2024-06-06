package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MoviesInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MoviesInfoController {

    private final MoviesInfoService moviesInfoService;


    @GetMapping("/movieInfos")
    public Flux<MovieInfo> getAllMovieInfos() {
        return moviesInfoService.getAllMovieInfos().log();
    }

    @GetMapping("/movieInfos/{id}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id) {
        return moviesInfoService.getMovieInfoById(id).log();
    }


    @PostMapping("/movieInfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo) {

        return moviesInfoService.addMovieInfo(movieInfo).log();
    }

    @PutMapping("/movieInfos/{id}")
    public Mono<MovieInfo> updateMovieInfo(@RequestBody MovieInfo updatedMovieInfo, @PathVariable String id) {

        return moviesInfoService.updateMovieInfo(updatedMovieInfo, id).log();
    }

    @DeleteMapping("/movieInfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id) {
        return moviesInfoService.deleteMovieInfo(id).log();
    }
}
