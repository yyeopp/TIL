package com.reactivespring.controller;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SinksTest {

    @Test
    void sink() {
        Sinks.Many<Integer> replaySink = Sinks.many().replay().all();

        replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux1 = replaySink.asFlux();
        integerFlux1.subscribe((i) -> {
            System.out.println("Subscriber 1 : " + i);
        });

        Flux<Integer> integerFlux2 = replaySink.asFlux();
        integerFlux2.subscribe((i) -> {
            System.out.println("Subscriber 2 : " + i);
        });

        replaySink.tryEmitNext(3);
        replaySink.tryEmitNext(4);

        Flux<Integer> integerFlux3 = replaySink.asFlux();
        integerFlux3.subscribe((i) -> {
            System.out.println("Subscriber 3 : " + i);
        });
    }

    @Test
    void sinks_multicast() {
        Sinks.Many<Integer> multicast = Sinks.many().multicast().onBackpressureBuffer();

        multicast.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux1 = multicast.asFlux();
        integerFlux1.subscribe((i) -> {
            System.out.println("Subscriber 1 : " + i);
        });

        Flux<Integer> integerFlux2 = multicast.asFlux();
        integerFlux2.subscribe((i) -> {
            System.out.println("Subscriber 2 : " + i);
        });

        multicast.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Test
    void sinks_unicast() {
        Sinks.Many<Integer> unicast = Sinks.many().unicast().onBackpressureBuffer();

        unicast.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        unicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux1 = unicast.asFlux();
        integerFlux1.subscribe((i) -> {
            System.out.println("Subscriber 1 : " + i);
        });

        Flux<Integer> integerFlux2 = unicast.asFlux();      // EXCEPTION
        integerFlux2.subscribe((i) -> {
            System.out.println("Subscriber 2 : " + i);
        });

        unicast.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
