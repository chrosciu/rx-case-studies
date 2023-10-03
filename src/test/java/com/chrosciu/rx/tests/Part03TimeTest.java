package com.chrosciu.rx.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
public class Part03TimeTest {

    @Test
    void shouldReturnDelayedValue() {
        //given
        Mono<String> mono = delayedMono();

        //when / then
        StepVerifier.create(mono)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(10))
                .expectNext("Marcin")
                .verifyComplete();
    }

    @Test
    void shouldReturnDelayedValueUsingVirtualTime() {
        //given
        Supplier<Mono<String>> monoSupplier = () -> delayedMono();

        //when / then
        StepVerifier.withVirtualTime(monoSupplier)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(10))
                .expectNext("Marcin")
                .verifyComplete();
    }

    private Mono<String> delayedMono() {
        return Mono.just("Marcin").delayElement(Duration.ofSeconds(10));
    }

}
