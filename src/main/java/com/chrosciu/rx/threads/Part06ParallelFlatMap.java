package com.chrosciu.rx.threads;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Part06ParallelFlatMap {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Flux<String> logins = Flux.just("A", "B")
                .publishOn(Schedulers.boundedElastic());

        log.info("Before subscribe");

        logins.doFinally(st -> latch.countDown())
                .flatMap(u -> save(u).subscribeOn(Schedulers.boundedElastic()))
                .subscribe(s -> log.info(s), e -> log.warn("", e));

        log.info("After subscribe");

        latch.await();
    }

    @SneakyThrows
    private static Mono<String> save(String login) {
        //blocks for long time
        Thread.sleep(1000);
        log.info("Blocking save !!! for " + login);
        return Mono.just(login);
    }
}
