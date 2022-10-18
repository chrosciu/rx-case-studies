package com.chrosciu.rx.operators;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;

@Slf4j
public class Debounce {
    @SneakyThrows
    public static void main(String[] args) {
        List<Tuple2<String, Integer>> itemsWithDelays = List.of(
                Tuples.of("A", 100),
                Tuples.of("B", 300),
                Tuples.of("C", 400),
                Tuples.of("D", 700),
                Tuples.of("E", 800)
        );

        Flux<String> flux = Flux.fromIterable(itemsWithDelays)
                .flatMap(t -> Mono.just(t.getT1())
                        .delayElement(Duration.ofMillis(t.getT2())));

        flux = flux.sampleTimeout(i -> Mono.just(i).delayElement(Duration.ofMillis(150)));

        flux.subscribe(i -> log.info("Item: {}", i),
                e -> log.warn("Error: ", e),
                () -> log.info("Completed"));

        Thread.sleep(1000);
    }
}
