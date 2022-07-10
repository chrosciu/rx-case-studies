package com.chrosciu.rx.hot;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Part02SimpleShare {
    public static void main(String[] args) {
        EventPublisher<String> eventPublisher = new EventPublisher<>();
        Flux<String> flux = Flux.<String>create(
                        sink -> eventPublisher.registerEventListener(sink::next))
                .log("[Original]");
        Flux<String> shared = flux.share().log("[Shared]");
        eventPublisher.emit("A");
        log.info("Subscribing 1");
        shared.subscribe(s -> log.info("S1: {}", s), e -> log.warn("Error: ", e));
        log.info("Subscribed 1");
        eventPublisher.emit("B");
        log.info("Subscribing 2");
        shared.subscribe(s -> log.info("S2: {}", s), e -> log.warn("Error: ", e));
        log.info("Subscribed 2");
        eventPublisher.emit("C");
    }
}
