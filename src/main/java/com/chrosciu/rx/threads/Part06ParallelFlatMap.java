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
        Flux<String> logins = Flux.just("A", "B");

        log.info("Before subscribe");

        logins
                .flatMap(u -> save(u).subscribeOn(Schedulers.boundedElastic()))
                .doFinally(st -> latch.countDown())
                .subscribe(s -> log.info(s), e -> log.warn("", e));

        log.info("After subscribe");

        latch.await();

        log.info("After all");
    }

    @SneakyThrows
    private static Mono<String> save(String login) {
        return Mono.fromCallable(() -> {
            try {
                log.info("Blocking save for " + login);
                Thread.sleep(1000);
                log.info("Blocking save - finish for " + login);
                return login + " saved";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
