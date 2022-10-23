package com.chrosciu.rx.subscription;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CallbackSubscriberExample {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 3).log();
//                .concatWith(Flux.error(new RuntimeException("blah"))).log();
        flux.subscribe(i -> log.info("Item in stream: {}", i),
                t -> log.warn("Error in stream", t),
                () -> log.info("Completed"));

        //flux.subscribe(i -> {});
        //flux.subscribe();
    }
}
