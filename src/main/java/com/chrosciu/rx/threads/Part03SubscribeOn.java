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

        Flux<String> flux = Flux.<String>create(sink -> {
                    log.info("Before sending A");
                    sink.next("A");
                    log.info("After sending A");
                    sink.next("B");
                    log.info("After sending B");
                    sink.complete();
                    log.info("After sending complete");
                })
                .log("Above subscribeOn")
                .subscribeOn(scheduler)
                .log("Below subscribeOn");

        log.info("Before subscribe");

        flux.doFinally(
                s -> countDownLatch.countDown()
        ).subscribe(
                i -> log.info("Item: {}", i),
                e -> log.warn("Error: ", e),
                () -> log.info("Completed")
        );

        log.info("After subscribe");

        countDownLatch.await();

        scheduler.dispose();
    }
}
