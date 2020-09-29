package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Part01FullSynchronous {
    public static void main(String[] args) {
        Flux<String> flux = Flux.create(sink -> {
            log.info("Before feeding sink");
            sink.next("A");
            log.info("After sending A");
            sink.next("B");
            log.info("After sending B");
            sink.complete();
            log.info("After feeding sink");
        });

        log.info("Before subscribe");

        flux.log().subscribe(i -> {
            log.info("Callback for item: {}", i);
        }, e-> {
            log.warn("Error:", e);
        }, () -> {
           log.info("Callback after complete");
        });

        log.info("After subscribe");
    }
}
