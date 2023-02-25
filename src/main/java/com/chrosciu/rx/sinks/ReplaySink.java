package com.chrosciu.rx.sinks;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class ReplaySink {
    @SneakyThrows
    public static void main(String[] args) {
        Sinks.Many<String> replaySink = Sinks.many().replay().all();
        Flux<String> flux = replaySink.asFlux();

        flux.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));

        Thread t = new Thread(() -> {
            replaySink.tryEmitNext("A");
            replaySink.tryEmitNext("B");
        });
        t.start();
        t.join();

        flux.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));

        t = new Thread(() -> {
            replaySink.tryEmitNext("C");
        });
        t.start();
        t.join();

        flux.subscribe(s -> log.info("[3] {}", s), e -> log.warn("[3]", e), () -> log.info("[3] completed"));

        t = new Thread(replaySink::tryEmitComplete);
        t.start();
        t.join();

        flux.subscribe(s -> log.info("[4] {}", s), e -> log.warn("[4]", e), () -> log.info("[4] completed"));

    }
}
