package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part03SubscribeOn {
    public static void main(String[] args) throws Exception {
        Scheduler scheduler = Schedulers.newElastic("E");

        Flux<String> flux = Flux.just("A", "B")
                .log("Above subscribeOn")
                .subscribeOn(scheduler)
                .log("Below subscribeOn");

        flux.subscribe(log::info);

        scheduler.dispose();
    }
}
