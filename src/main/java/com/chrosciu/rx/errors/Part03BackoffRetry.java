package com.chrosciu.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Part03BackoffRetry {
    public static void main(String[] args) throws Exception {
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);

        Flux<String> original = Flux.just("A", "B").delayElements(Duration.ofMillis(50));
        Mono<String> error = Mono.fromCallable(() -> {
            if (counter.getAndIncrement() < 2) {
                throw new RuntimeException("Blah!");
            }
            return null;
        });
        Flux<String> flux = Flux.concat(original, error);

        Flux<String> withBackoff = flux
                .retryWhen(Retry.backoff(3, Duration.ofMillis(200)));

        withBackoff
                .elapsed()
                .doFinally(st -> latch.countDown())
                .subscribe(
                i -> log.info("Item: {}", i),
                e -> log.info("Error: ", e),
                () -> log.info("Completed")
        );

        latch.await();
    }
}
