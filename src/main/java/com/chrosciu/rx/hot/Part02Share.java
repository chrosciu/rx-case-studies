package com.chrosciu.rx.hot;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Slf4j
public class Part02Share {
    public static void main(String[] args) {
        EventPublisher<String> eventPublisher = new EventPublisher<>();
        SimpleSubscriber<String> subscriber1 = new SimpleSubscriber<>("S1");
        SimpleSubscriber<String> subscriber2 = new SimpleSubscriber<>("S2");
        Flux<String> flux = Flux.<String>create(
                        sink -> eventPublisher.registerEventListener(sink::next),
                        FluxSink.OverflowStrategy.ERROR)
                .log("[Original]");
        //Flux<String> shared = flux.share().log("[Shared]");
        Flux<String> shared = flux.publish(1).refCount().log("[Shared]");
        eventPublisher.emit("A");
        log.info("Subscribing 1");
        shared.subscribe(subscriber1);
        log.info("Subscribed 1");
        eventPublisher.emit("B");
        log.info("S1: requesting 1");
        subscriber1.request(1);
        log.info("S1: requested 1");
        eventPublisher.emit("C");
        log.info("Subscribing 2");
        shared.subscribe(subscriber2);
        log.info("Subscribed 2");
        eventPublisher.emit("D");
        log.info("S2: requesting 2");
        subscriber2.request(2);
        log.info("S2: requested 2");
        log.info("S1: requesting 1");
        subscriber1.request(1);
        log.info("S1: requested 1");
        eventPublisher.emit("E");
        log.info("Cancelling 1");
        subscriber1.cancel();
        log.info("Cancelled 1");
        log.info("Cancelling 2");
        subscriber2.cancel();
        log.info("Cancelled 2");
    }
}
