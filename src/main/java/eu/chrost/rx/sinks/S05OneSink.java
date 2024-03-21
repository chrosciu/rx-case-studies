package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
class S05OneSink {
    public static void main(String[] args) {
        Sinks.One<String> sink = Sinks.one();
        Mono<String> mono = sink.asMono();

        log.info("[1] Before subscribe");
        mono.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));
        log.info("[1] After subscribe");

        log.info("Before emitting A");
        sink.tryEmitValue("A");
        //sink.tryEmitError(new RuntimeException("Blah"));
        log.info("After emitting A");

        log.info("[2] Before subscribe");
        mono.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));
        log.info("[2] After subscribe");
    }
}
