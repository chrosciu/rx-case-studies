package com.chrosciu.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Part03FalsePositiveTest {
    @Test
    public void neverMonoTest() {
        Mono<String> neverMono = Mono.never();
        StepVerifier.create(neverMono)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();
    }

    @Test
    public void emptyMonoTest() {
        Mono<String> neverMono = Mono.empty();
        StepVerifier.create(neverMono)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();
    }
}
