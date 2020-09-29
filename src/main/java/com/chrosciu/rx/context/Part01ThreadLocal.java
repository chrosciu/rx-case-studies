package com.chrosciu.rx.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part01ThreadLocal {
    public static void main(String[] args) throws Exception {
        ThreadLocal<String> rid = new ThreadLocal<>();
        Flux<Integer> data = Flux.just(3, 5, 7).publishOn(Schedulers.elastic());
        Flux<Integer> logged = data.doOnNext(i -> log.info("RequestId: [{}] {}", rid.get(), i));
        rid.set("f00d");
        logged.subscribe();

        Thread.sleep(1000);
    }
}
