package com.chrosciu.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Part05IgnoreItemWithError {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(1, 10)
                .flatMap(Part05IgnoreItemWithError::transformAsync);
        flux.subscribe(i -> log.info("Item: {}", i),
                e -> log.warn("Error: ", e));
    }

    private static int transform(int i) {
        if (3 == i) {
            throw new RuntimeException("blah");
        }
        return i + 1;
    }

    private static Mono<Integer> transformAsync(int i) {
        return Mono.just(i)
                .map(Part05IgnoreItemWithError::transform)
                .onErrorResume(e -> Mono.empty());
    }
}
