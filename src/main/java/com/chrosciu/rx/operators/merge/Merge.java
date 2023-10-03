package com.chrosciu.rx.operators.merge;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class Merge {
    @SneakyThrows
    public static void main(String[] args) {
        List<Tuple2<String, Duration>> flux1Items = List.of(
                Tuples.of("Marcin", Duration.ofMillis(300)),
                Tuples.of("Tomasz", Duration.ofMillis(500)),
                Tuples.of("Pawel", Duration.ofMillis(800))
        );

        Flux<String> flux1 = fluxWithDelays(flux1Items).log("flux1");

        List<Tuple2<String, Duration>> flux2Items = List.of(
                Tuples.of("Aleksandra", Duration.ofMillis(200)),
                Tuples.of("Joanna", Duration.ofMillis(400)),
                Tuples.of("Maria", Duration.ofMillis(900))
        );

        Flux<String> flux2 = fluxWithDelays(flux2Items).log("flux2");

        //Flux<String> flux = Flux.merge(flux1, flux2);
        //Flux<String> flux = flux1.mergeWith(flux2);
        //Flux<String> flux = Flux.concat(flux1, flux2);
        Flux<String> flux = Flux.mergeSequential(flux1, flux2);

        flux.subscribe(s -> log.info("Item: {}", s),
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
