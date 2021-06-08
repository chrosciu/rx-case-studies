package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part02PublishOn {
    public static void main(String[] args) throws Exception {
        Scheduler scheduler1 = Schedulers.newElastic("S1");
        Scheduler scheduler2 = Schedulers.newElastic("S2");
        Scheduler scheduler3 = Schedulers.newElastic("S3");
        Scheduler scheduler4 = Schedulers.newElastic("S4");

        Flux<String> flux = Flux.just("A", "B")
            .subscribeOn(scheduler1)
            .publishOn(scheduler2)
            .map(s -> {
                log.info("In map: " + s);
                return s;
            })
            .subscribeOn(scheduler3)
            .publishOn(scheduler4)
            .doFinally(s -> {
                scheduler1.dispose();
                scheduler2.dispose();
                scheduler3.dispose();
                scheduler4.dispose();
            });

        flux.subscribe(log::info);



//        scheduler.dispose();
//        scheduler2.dispose();
    }
}
