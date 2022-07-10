package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Part03SubscribeOn {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Scheduler scheduler = Schedulers.newBoundedElastic(10, 100, "E");
        Scheduler scheduler2 = Schedulers.newBoundedElastic(10, 100,"EE");

        Flux<String> flux = Flux.<String>create(sink -> {
                    log.info("Before sending A");
                    sink.next("A");
                    log.info("After sending A");
                    sink.next("B");
                    log.info("After sending B");
                    sink.complete();
                    log.info("After sending complete");
                })
                .subscribeOn(scheduler)
                .publishOn(scheduler2);


        flux.doFinally(s -> countDownLatch.countDown()).subscribe(log::info, e -> log.warn("", e), () -> log.info("Completed"));

        countDownLatch.await();

        scheduler.dispose();
        scheduler2.dispose();
    }
}
