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
        Duration duration = StepVerifier.create(mono)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(10))
                .expectNext("Marcin")
                .verifyComplete();
        log.info("shouldReturnDelayedValue - duration: {}", duration);
    }

    @Test
    void shouldReturnDelayedValueUsingVirtualTime() {
        //given
        Supplier<Mono<String>> monoSupplier = () -> delayedMono();

        //when / then
        Duration duration = StepVerifier.withVirtualTime(monoSupplier)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(10))
                .expectNext("Marcin")
                .verifyComplete();
        log.info("shouldReturnDelayedValueUsingVirtualTime - duration: {}", duration);
    }

    private Mono<String> delayedMono() {
        return Mono.just("Marcin").delayElement(Duration.ofSeconds(10));
    }

}
