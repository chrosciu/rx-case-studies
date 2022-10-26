package com.chrosciu.rx.subscription;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Slf4j
public class CallbackSubscribers {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 3)
                .concatWith(Flux.error(new RuntimeException("blah"))).log();
        Disposable disposable = flux.subscribe(i -> log.info("Item in stream: {}", i),
                t -> log.warn("Error in stream", t),
                () -> log.info("Completed"));
        disposable.dispose();

//        flux.subscribe(i -> log.info("Item in stream: {}", i),
//                t -> log.warn("Error in stream", t));

//        flux.subscribe(i -> log.info("Item in stream: {}", i));

//        flux.subscribe(i -> log.info("Item in stream: {}", i),
//                t -> {});

//        flux.subscribe(i -> {});

//        flux.subscribe();
    }
}
