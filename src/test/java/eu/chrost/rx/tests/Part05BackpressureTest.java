package eu.chrost.rx.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

class Part05BackpressureTest {
    @Test
    void backpressureTest() {
        //given
        Flux<Long> flux = flux();

        //when / then
        StepVerifier.create(flux, 1)
                .expectNext(0L)
                .thenRequest(1)
                .expectNext(1L)
                .thenCancel()
                .verify();
    }

    private Flux<Long> flux() {
        return Flux.just(0L, 1L, 2L);
    }
}
