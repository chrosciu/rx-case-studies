package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part03SubscribeOn {
    public static void main(String[] args) throws Exception {
        Scheduler scheduler = Schedulers.newElastic("E");

        Flux<String> flux = Flux.create(sink -> {
            log.info("Before feeding sink");
            sink.next("A");
            log.info("After sending A");
            sink.next("B");
            log.info("After sending B");
            sink.complete();
            log.info("After feeding sink");
        });

        Flux<String> flux1 =
                flux
                .log("Above subscribeOn")
                .subscribeOn(scheduler)
                .log("Below subscribeOn")
                .doFinally(s -> {
                    scheduler.dispose();
                });

        flux1.subscribe(log::info);

        //scheduler.dispose();
    }
}
