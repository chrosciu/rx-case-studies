package com.chrosciu.rx.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Part02SimpleTest {
    @Test
    public void simpleFluxTest() {
        //given
        Flux<String> flux = simpleFlux();

        //when - then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectNext("B")
                .verifyComplete();
    }

    private Flux<String> simpleFlux() {
        return Flux.just("A", "B");
    }

    @Test
    public void errorFluxTest() {
        //given
        Flux<String> flux = errorFlux();

        //when - then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectNext("B")
                .expectError(RuntimeException.class)
                .verify();
    }

    private Flux<String> errorFlux() {
        return Flux.just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Blah!")))
                .concatWith(Flux.just("C"));
    }
}
