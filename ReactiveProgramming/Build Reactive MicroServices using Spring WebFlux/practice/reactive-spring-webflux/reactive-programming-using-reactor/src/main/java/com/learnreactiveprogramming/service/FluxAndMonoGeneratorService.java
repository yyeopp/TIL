package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.just("alex", "ben", "chloe");
    }

    public Mono<String> namesMono() {
        return Mono.just("alex");
    }

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux().subscribe((name) -> {
            System.out.println("Name is : " + name);
        });

        fluxAndMonoGeneratorService.namesMono().subscribe((name) -> {
            System.out.println("Name is : " + name);
        });
    }
}
