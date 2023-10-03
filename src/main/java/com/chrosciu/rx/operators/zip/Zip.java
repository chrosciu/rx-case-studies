package com.chrosciu.rx.operators.zip;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;
import java.util.function.BiFunction;

@Slf4j
public class Zip {
    @SneakyThrows
    public static void main(String[] args) {
        List<Tuple2<String, Duration>> flux1Items = List.of(
                Tuples.of("Marcin", Duration.ofMillis(300)),
                Tuples.of("Tomasz", Duration.ofMillis(500)),
                Tuples.of("Pawel", Duration.ofMillis(800))
        );

        Flux<String> flux1 = fluxWithDelays(flux1Items).log("flux1");

        List<Tuple2<Long, Duration>> flux2Items = List.of(
                Tuples.of(200L, Duration.ofMillis(200)),
                Tuples.of(400L, Duration.ofMillis(400))
        );

        Flux<Long> flux2 = fluxWithDelays(flux2Items).log("flux2");

        //Flux<Tuple2<String, Long>> flux = Flux.zip(flux1, flux2);
        Flux<Tuple2<String, Long>> flux = Flux.combineLatest(flux1, flux2,
                (s, aLong) -> Tuples.of(s, aLong));

                flux.subscribe(t2 -> log.info("Item: {}", t2),
                        t -> log.info("Error: ", t),
                        () -> log.info("Completed"));

        Thread.sleep(3000);
    }

    private static <T> Flux<T> fluxWithDelays(List<Tuple2<T, Duration>> items) {
        Flux<Tuple2<T, Duration>> itemsFlux = Flux.fromIterable(items);
        Flux<T> resultFlux = itemsFlux
                .flatMap(item -> Mono.just(item.getT1()).delayElement(item.getT2()));
        return resultFlux;
    }
}
