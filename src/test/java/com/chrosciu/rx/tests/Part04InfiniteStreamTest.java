package com.chrosciu.rx.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Part04InfiniteStreamTest {

    @Test
    void infiniteFluxTest() {
        //given
        Flux<Long> flux = inifiniteFlux();

        //when / then
        StepVerifier.create(flux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .thenCancel()
                .verify();
    }

    private Flux<Long> inifiniteFlux() {
        return Flux.interval(Duration.ofMillis(1));
    }
}
