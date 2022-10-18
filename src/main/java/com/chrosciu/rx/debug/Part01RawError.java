package com.chrosciu.rx.debug;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Part01RawError {
    public static void main(String[] args) {
        Mono<Integer> mono = badStream();
        mono.subscribe(i -> log.info("Item: {}", i), e -> log.warn("Error in stream: ", e));
    }

    private static Mono<Integer> badStream() {
        return Flux.range(0, 10)
                .map(i -> i * 2)
                .filter(i -> i % 3 != 0)
                .map(i -> i + 1)
                .elementAt(8)
                .filter(i -> i > 0);
    }
}
