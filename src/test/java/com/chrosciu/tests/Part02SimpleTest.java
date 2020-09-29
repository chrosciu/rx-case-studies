package com.chrosciu.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Part02SimpleTest {
    @Test
    public void simpleTest() {
        //given
        Flux<String> flux = Flux.just("A", "B");

        //when - then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectNext("B")
                .verifyComplete();
    }
}
