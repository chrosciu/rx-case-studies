package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part05ReactiveToBlocking {

    public static void main(String[] args) throws Exception {
        Flux<String> logins = Flux.just("A", "B").publishOn(Schedulers.boundedElastic());

        logins.doOnNext(u -> save(u)).subscribe(s -> log.info(s), e -> log.warn("", e));


        Thread.sleep(1000);
    }

    private static void save(String login) {
        //blocks for long time
        log.info("Blocking save !!! for " + login);
    }
}
