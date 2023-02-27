package com.chrosciu.rx.sinks;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class MulticastSink {
    @SneakyThrows
    public static void main(String[] args) {
        Sinks.Many<String> multicastSink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<String> flux = multicastSink.asFlux();

        flux.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));

        Thread t = new Thread(() -> {
            multicastSink.tryEmitNext("A");
            multicastSink.tryEmitNext("B");
        });
        t.start();
        t.join();

        flux.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));

        t = new Thread(() -> {
            multicastSink.tryEmitNext("C");
        });
        t.start();
        t.join();

        flux.subscribe(s -> log.info("[3] {}", s), e -> log.warn("[3]", e), () -> log.info("[3] completed"));

        t = new Thread(multicastSink::tryEmitComplete);
        t.start();
        t.join();

        flux.subscribe(s -> log.info("[4] {}", s), e -> log.warn("[4]", e), () -> log.info("[4] completed"));

    }
}
