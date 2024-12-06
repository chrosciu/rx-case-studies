package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Slf4j
class Part03ManyUnicastSink {
    public static void main(String[] args) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();
//        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureError();
//        Queue<String> queue = new LinkedBlockingQueue<>(1);
//        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer(queue);
        Flux<String> flux = sink.asFlux();

        log.info("sink.tryEmitNext(A)");
        EmitResult emitResult = sink.tryEmitNext("A");
        log.info("sink.tryEmitNext(A) => {}", emitResult);

        log.info("sink.tryEmitNext(B)");
        emitResult = sink.tryEmitNext("B");
        log.info("sink.tryEmitNext(B) => {}", emitResult);

        String subscriberOneLogPrefix = "1";
        BaseSubscriber<String> subscriberOne = new LazyLoggingSubscriber<>(subscriberOneLogPrefix);
        log.info("[{}] Before subscribe", subscriberOneLogPrefix);
        flux.subscribe(subscriberOne);
        log.info("[{}] After subscribe", subscriberOneLogPrefix);

        log.info("sink.tryEmitNext(C)");
        emitResult = sink.tryEmitNext("C");
        log.info("sink.tryEmitNext(C) => {}", emitResult);

        log.info("[{}] Before request(1)", subscriberOneLogPrefix);
        subscriberOne.request(1);
        log.info("[{}] After request(1)", subscriberOneLogPrefix);

        log.info("sink.tryEmitNext(D)");
        emitResult = sink.tryEmitNext("D");
        log.info("sink.tryEmitNext(D) => {}", emitResult);

        log.info("[{}] Before request(1)", subscriberOneLogPrefix);
        subscriberOne.request(1);
        log.info("[{}] After request(1)", subscriberOneLogPrefix);

        String subscriberTwoLogPrefix = "2";
        BaseSubscriber<String> subscriberTwo = new EagerLoggingSubscriber<>(subscriberTwoLogPrefix);
        log.info("[{}] Before subscribe", subscriberTwoLogPrefix);
        flux.subscribe(subscriberTwo);
        log.info("[{}] After subscribe", subscriberTwoLogPrefix);

        log.info("[{}] Before dispose", subscriberOneLogPrefix);
        subscriberOne.dispose();
        log.info("[{}] After dispose", subscriberOneLogPrefix);

        log.info("[{}] Before dispose", subscriberTwoLogPrefix);
        subscriberTwo.dispose();
        log.info("[{}] After dispose", subscriberTwoLogPrefix);
    }
}
