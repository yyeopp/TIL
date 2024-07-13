package com.reactivespring.client;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.exception.MoviesInfoClientException;
import com.reactivespring.exception.MoviesInfoServerException;
import com.reactivespring.util.RetryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoviesInfoRestClient {

    private final WebClient webClient;

    @Value("${restClient.moviesInfoUrl}")
    private String moviesInfoUrl;

    public Mono<MovieInfo> retrieveMovieInfo(String movieId) {

        var url = moviesInfoUrl.concat("/{id}");

        return webClient.get()
                .uri(url, movieId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(
                                new MoviesInfoClientException("NO", clientResponse.statusCode().value()));
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage
                                    -> Mono.error(new MoviesInfoClientException(responseMessage, clientResponse.statusCode().value())));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage
                                    -> Mono.error(new MoviesInfoServerException("Server Exception " + responseMessage)));
                })
                .bodyToMono(MovieInfo.class)
                .retryWhen(RetryUtil.retrySpec())
                .log();
    }
}
