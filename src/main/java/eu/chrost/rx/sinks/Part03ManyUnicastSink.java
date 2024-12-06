package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
class Part03ManyUnicastSink {
    public static void main(String[] args) {
//        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureError();
//        Queue<String> queue = new LinkedBlockingQueue<>(1);
//        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer(queue);
        Flux<String> flux = sink.asFlux();

        log.info("sink.tryEmitNext(A)");
        EmitResult emitResult = sink.tryEmitNext("A");
        log.info("sink.tryEmitNext(A) => {}", emitResult);

        log.info("sink.tryEmitNext(B)");
        emitResult = sink.tryEmitNext("B");
        log.info("sink.tryEmitNext(B) => {}", emitResult);

        log.info("[1] Before first subscribe");
        flux.subscribe(s -> log.info("[1] {}", s), e -> log.warn("[1]", e), () -> log.info("[1] completed"));
        log.info("[1] After first subscribe");

        log.info("sink.tryEmitNext(C)");
        emitResult = sink.tryEmitNext("C");
        log.info("sink.tryEmitNext(C) => {}", emitResult);

        log.info("sink.tryEmitNext(D)");
        emitResult = sink.tryEmitNext("D");
        log.info("sink.tryEmitNext(D) => {}", emitResult);

        log.info("[2] Before second subscribe");
        flux.subscribe(s -> log.info("[2] {}", s), e -> log.warn("[2]", e), () -> log.info("[2] completed"));
        log.info("[2] After second subscribe");

        log.info("sink.tryEmitComplete()");
        emitResult = sink.tryEmitComplete();
        log.info("sink.tryEmitComplete() => {}", emitResult);
    }
}
