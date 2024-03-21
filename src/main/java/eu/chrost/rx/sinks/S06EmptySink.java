package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
class S06EmptySink {
    public static void main(String[] args) {
        Sinks.Empty<String> sink = Sinks.empty();
        Mono<String> mono = sink.asMono();

        log.info("[1] Before subscribe");
        mono.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));
        log.info("[1] After subscribe");

        log.info("Before emitting complete");
        sink.tryEmitEmpty();
        //sink.tryEmitError(new RuntimeException("Blah"));
        log.info("After emitting complete");

        log.info("[2] Before subscribe");
        mono.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));
        log.info("[2] After subscribe");
    }
}
