package com.chrosciu.rx.debug;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Part04Checkpoint {
    public static void main(String[] args) {
        Mono<Integer> mono = badStream();
        mono.subscribe(System.out::println, e -> log.warn("Error in stream: ", e));
    }

    private static Mono<Integer> badStream() {
        return Flux.range(0, 10)
                .map(i -> i * 2)
                .filter(i -> i % 3 != 0)
                .map(i -> i + 1)
                //.checkpoint()
                .elementAt(8)
                //.checkpoint()
                .filter(i -> i > 0)
                //.checkpoint();
                .checkpoint("After filter");
    }
}
