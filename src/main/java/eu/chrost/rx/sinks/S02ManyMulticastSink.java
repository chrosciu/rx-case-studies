package eu.chrost.rx.sinks;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
class S02ManyMulticastSink {
    @SneakyThrows
    public static void main(String[] args) {
        Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
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

        log.info("Before emitting C");
        sink.tryEmitNext("C");
        log.info("After emitting C");

        log.info("[3] Before subscribe");
        flux.subscribe(s -> log.info("[3] {}", s), e -> log.warn("[3]", e), () -> log.info("[3] completed"));
        log.info("[3] After subscribe");

        log.info("Before emitting complete");
        sink.tryEmitComplete();
        log.info("After emitting complete");

        log.info("[4] Before subscribe");
        flux.subscribe(s -> log.info("[4] {}", s), e -> log.warn("[4]", e), () -> log.info("[4] completed"));
        log.info("[4] After subscribe");
    }
}
