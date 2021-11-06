package com.chrosciu.rx.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ErrorHandling {
    public static void main(String[] args) {
        Flux<String> errorStream = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("boom")))
                .onErrorMap(e -> new IllegalStateException("bad"));



        errorStream.subscribe(s -> log.info("Element received: {}", s), e -> log.warn("Error occured: ", e), () -> log.info("Stream completed"));
    }
}
