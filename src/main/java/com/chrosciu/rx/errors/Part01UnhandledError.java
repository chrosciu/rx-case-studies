package com.chrosciu.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Part01UnhandledError {
    public static void main(String[] args) {
        Flux<String> flux = Flux
                .just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Blah!")))
                .onErrorReturn("DUMMY");

        flux.subscribe(s -> log.info("Item: {}", s));

//        flux.subscribe(s -> log.info("Item: {}", s),
//                e -> log.warn("Error in stream: ", e),
//                () -> log.info("Completed"));

//        flux.subscribe(s -> log.info("Item: {}", s),
//                e -> {},
//                () -> log.info("Completed"));

        log.info("Terminated normally");
    }
}
