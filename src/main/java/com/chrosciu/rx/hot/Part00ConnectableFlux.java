package com.chrosciu.rx.hot;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@Slf4j
public class Part00ConnectableFlux {
    public static void main(String[] args) {
        EventPublisher<String> eventPublisher = new EventPublisher<>();
        Flux<String> flux = Flux.<String>create(
                        sink -> eventPublisher.registerEventListener(sink::next))
                .log("[Original]");
        ConnectableFlux<String> connectableRaw = flux.replay();//.log("[Connectable]");
        Flux<String> connectable = connectableRaw.log("[Connectable]");
        eventPublisher.emit("A");
        log.info("Subscribing 1");
        connectable.subscribe(s -> log.info("S1: {}", s), e -> log.warn("Error: ", e));
        log.info("Subscribed 1");
        eventPublisher.emit("B");
        log.info("Subscribing 2");
        connectable.subscribe(s -> log.info("S2: {}", s), e -> log.warn("Error: ", e));
        log.info("Subscribed 2");
        eventPublisher.emit("C");
        log.info("Connecting");
        Disposable connection = connectableRaw.connect();
        log.info("Connected");
        eventPublisher.emit("D");
        log.info("Subscribing 3");
        connectable.subscribe(s -> log.info("S3: {}", s), e -> log.warn("Error: ", e));
        log.info("Subscribed 3");
        eventPublisher.emit("E");
        log.info("Disconnecting");
        connection.dispose();
        log.info("Disconnected");
//        eventPublisher.emit("F");
    }
}
