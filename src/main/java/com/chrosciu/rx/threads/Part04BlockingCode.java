package com.chrosciu.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Part04BlockingCode {
    public static void main(String[] args) throws Exception {
        Flux<String> logins = Flux.defer(() -> Flux.fromIterable(getUserLogins())).subscribeOn(Schedulers.elastic());
        log.info("Before subscribe");

        logins.subscribe(s -> log.info(s), e -> log.warn("", e));

        log.info("After subscribe");

        Thread.sleep(1000);

    }

    private static List<String> getUserLogins() {
        //blocks for long time
        log.info("Blocking !!!");
        return Arrays.asList("A", "B");
    }
}
