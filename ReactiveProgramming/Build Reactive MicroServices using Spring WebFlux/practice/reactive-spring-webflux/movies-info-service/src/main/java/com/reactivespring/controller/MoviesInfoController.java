package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MoviesInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MoviesInfoController {

    private final MoviesInfoService moviesInfoService;

    Sinks.Many<MovieInfo> moviesInfoSink = Sinks.many().replay().latest();

    @GetMapping("/movieInfos")
    public Flux<MovieInfo> getAllMovieInfos(@RequestParam(value = "year", required = false) Integer year) {
        if (year != null) {
            return moviesInfoService.getMovieInfoByYear(year);
        }
        return moviesInfoService.getAllMovieInfos().log();
    }

    @GetMapping("/movieInfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> getMovieInfoById(@PathVariable String id) {
        return moviesInfoService.getMovieInfoById(id)
                .map(movieInfo -> ResponseEntity.ok().body(movieInfo))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @GetMapping(value = "/movieInfos/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieInfo> getMovieInfoById() {
        return moviesInfoSink.asFlux();
    }

    @PostMapping("/movieInfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {

        return moviesInfoService.addMovieInfo(movieInfo)
                .doOnNext(savedInfo -> moviesInfoSink.tryEmitNext(savedInfo))
                .log();

    }

    @PutMapping("/movieInfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@RequestBody @Valid MovieInfo updatedMovieInfo, @PathVariable String id) {

        return moviesInfoService.updateMovieInfo(updatedMovieInfo, id)
                .map(movieInfo -> ResponseEntity.ok().body(movieInfo))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @DeleteMapping("/movieInfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id) {
        return moviesInfoService.deleteMovieInfo(id).log();
    }


}
