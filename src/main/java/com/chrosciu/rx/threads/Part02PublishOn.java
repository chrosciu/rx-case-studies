package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part02PublishOn {
    public static void main(String[] args) {
        Scheduler scheduler = Schedulers.newElastic("E");

        Flux<String> flux = Flux.just("A", "B")
            .log("Above publishOn")
            .publishOn(scheduler)
            .log("Below publishOn");

        flux.subscribe(log::info);

        scheduler.dispose();
    }
}
