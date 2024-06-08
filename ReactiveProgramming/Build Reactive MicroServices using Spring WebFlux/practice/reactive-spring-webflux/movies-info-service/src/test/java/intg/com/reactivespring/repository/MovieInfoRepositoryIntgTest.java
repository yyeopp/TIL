package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest      // IntegrationTest for Repository
@ActiveProfiles("test")
class MovieInfoRepositoryIntgTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieInfos)
                .blockLast();       // SYNC 로 종료되도록
    }

    @AfterEach
    void tearDown () {
        movieInfoRepository.deleteAll().block();
    }
    @Test
    void findAll() {
        var moviesInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        var moviesInfoMono = movieInfoRepository.findById("abc").log();

        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {
        var movieInfo = new MovieInfo(null, "Batman Begins1",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        var moviesInfoMono = movieInfoRepository.save(movieInfo).log();

        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo1 -> {
                    assertNotNull(movieInfo1.getMovieInfoId());
                    assertEquals("Batman Begins1", movieInfo1.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {

        var movieInfo = movieInfoRepository.findById("abc").block();
        movieInfo.setYear(2021);

        var moviesInfoMono = movieInfoRepository.save(movieInfo).log();
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo1 -> {
                    assertEquals(2021, movieInfo1.getYear());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {

        movieInfoRepository.deleteById("abc").block();
        var moviesInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findByYear() {
        var moviesInfoFlux = movieInfoRepository.findByYear(2005).log();
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
    @Test
    void findByName() {
        var moviesInfoFlux = movieInfoRepository.findByName("Batman Begins").log();
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
}