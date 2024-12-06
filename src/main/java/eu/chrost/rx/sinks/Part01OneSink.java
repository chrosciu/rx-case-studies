package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Slf4j
class Part01OneSink {
    public static void main(String[] args) {
        Sinks.One<String> sink = Sinks.one();
        Mono<String> mono = sink.asMono();

        log.info("sink.tryEmitValue(A)");
        EmitResult emitResult = sink.tryEmitValue("A");
//        EmitResult emitResult = sink.tryEmitEmpty();
//        EmitResult emitResult = sink.tryEmitError(new RuntimeException("Blah!"));
        log.info("sink.tryEmitValue(A) => {}", emitResult);

        log.info("[1] Before first subscribe");
        mono.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));
        log.info("[1] After first subscribe");

        log.info("sink.tryEmitValue(B)");
        emitResult = sink.tryEmitValue("A");
        log.info("sink.tryEmitValue(B) => {}", emitResult);

        log.info("[2] Before second subscribe");
        mono.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));
        log.info("[2] After second subscribe");
    }
}
