package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Slf4j
class Part02EmptySink {
    public static void main(String[] args) {
        Sinks.Empty<String> sink = Sinks.empty();
        Mono<String> mono = sink.asMono();

        log.info("sink.tryEmitEmpty()");
        EmitResult emitResult = sink.tryEmitEmpty();
        log.info("sink.tryEmitEmpty() => {}", emitResult);

        log.info("[1] Before first subscribe");
        mono.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));
        log.info("[1] After first subscribe");

        log.info("sink.tryEmitEerror()");
        emitResult = sink.tryEmitError(new RuntimeException("Blah!"));
        log.info("sink.tryEmitError() => {}", emitResult);

        log.info("[2] Before second subscribe");
        mono.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));
        log.info("[2] After second subscribe");
    }
}
