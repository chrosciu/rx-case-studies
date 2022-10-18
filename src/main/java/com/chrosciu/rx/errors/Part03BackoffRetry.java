package com.chrosciu.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
public class Part03BackoffRetry {
    public static void main(String[] args) throws Exception {
        Flux<String> original = Flux.just("A", "B").delayElements(Duration.ofMillis(50));
        Flux<String> error = Flux.error(new RuntimeException("Baadf00d"));
        Flux<String> flux = Flux.concat(original, error);

        Flux<String> withBackoff = flux.retryWhen(Retry.backoff(2, Duration.ofMillis(200)));
        withBackoff.elapsed().subscribe(System.out::println, e -> log.warn("error:", e));

        Thread.sleep(5000);
    }
}
