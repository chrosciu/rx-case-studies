package eu.chrost.rx.hot;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Part01SimpleCache {
    public static void main(String[] args) {
        EventPublisher<String> eventPublisher = new EventPublisher<>();
        Flux<String> flux = Flux.<String>create(
                        sink -> eventPublisher.registerEventListener(sink::next))
                .log("[Original]");
        Flux<String> cached = flux.cache().log("[Cached]");
        eventPublisher.emit("A");
        log.info("Subscribing 1");
        cached.subscribe(s -> log.info("S1: {}", s), e -> log.warn("Error: ", e));
        log.info("Subscribed 1");
        eventPublisher.emit("B");
        log.info("Subscribing 2");
        cached.subscribe(s -> log.info("S2: {}", s), e -> log.warn("Error: ", e));
        log.info("Subscribed 2");
        eventPublisher.emit("C");
    }
}
