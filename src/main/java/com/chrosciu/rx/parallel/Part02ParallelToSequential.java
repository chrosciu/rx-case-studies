package com.chrosciu.rx.parallel;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part02ParallelToSequential {
    public static void main(String[] args) throws Exception {
        Flux<Integer> flux = Flux.range(1, 10);
        ParallelFlux<Integer> parallelFlux = flux.parallel()
                .runOn(Schedulers.parallel())
                .map(i -> i * 2)
                .log("[Parallel]");
        Flux<Integer> mergedFlux = parallelFlux.sequential()
                .map(i -> i + 1)
                .log("[Sequential]");

        mergedFlux.subscribe(i -> log.info("Item: {}", i), e -> log.warn("", e), () -> log.info("completed"));
        Thread.sleep(1000);
    }
}
