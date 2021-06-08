package com.chrosciu.rx.hot;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Part02Defer {

    public static void main(String[] args) {
        //Flux<String> flux = Flux.fromIterable(blockingMethod()).log();
        log.info("Before flux creation");
        Flux<String> flux = Flux.defer(() -> Flux.fromIterable(blockingMethod())).cache().log();
        log.info("After flux creation");
        flux.subscribe(s -> log.info("s1: {}", s));
        log.info("After sub 1");
        flux.subscribe(s -> log.info("s2: {}", s));
        log.info("After sub 2");

    }

    private static List<String> blockingMethod() {
        log.info("Blocking !");
        return Arrays.asList("A", "B");
    }

}
