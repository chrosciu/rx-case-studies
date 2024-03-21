package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
class S04ManyUnicastSink {
    public static void main(String[] args) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> flux = sink.asFlux();

        log.info("[1] Before subscribe");
        flux.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));
        log.info("[1] After subscribe");

        log.info("Before emitting A & B");
        sink.tryEmitNext("A");
        sink.tryEmitNext("B");
        log.info("After emitting A & B");

        log.info("[2] Before subscribe");
        flux.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));
        log.info("[2] After subscribe");
    }
}
