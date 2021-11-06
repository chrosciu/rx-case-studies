package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part02PublishOn {
    public static void main(String[] args) {
        Scheduler scheduler = Schedulers.newElastic("E");
        Scheduler scheduler2 = Schedulers.newElastic("EE");

        Flux<String> flux = Flux.<String>create(sink -> {
                log.info("Before sending A");
                sink.next("A");
                log.info("After sending A");
                sink.next("B");
                log.info("After sending B");
                sink.complete();
                log.info("After sending complete");
            })
            .log("Above publishOn")
            .publishOn(scheduler)
            .log("Below publishOn")
            .publishOn(scheduler2)
            .log("Below second publishOn");

        flux.subscribe(log::info, e -> log.warn("", e), () -> log.info("Completed"));

        scheduler.dispose();
        scheduler2.dispose();
    }
}
