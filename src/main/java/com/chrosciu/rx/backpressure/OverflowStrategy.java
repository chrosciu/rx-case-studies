package com.chrosciu.rx.backpressure;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.stream.IntStream;

@Slf4j
class LazySubscriber<T> extends BaseSubscriber<T> {
    @Override
    public void hookOnSubscribe(Subscription s) {
    }

    public void hookOnNext(T t) {
        log.info("{}", t);
    }

    @Override
    public void hookOnError(Throwable t) {
        log.warn("Error: ", t);
    }

    @Override
    public void hookOnComplete() {
        log.info("Completed");
    }
}

@Slf4j
public class OverflowStrategy {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.<Integer>create(sink -> {
            log.info("Before sink");
            IntStream.range(1, 1000).forEach(i -> {
                log.info("Feeding sink with: {}", i);
                sink.next(i);
            });
            sink.complete();
            log.info("After sink");
        }, FluxSink.OverflowStrategy.ERROR);

        //flux.subscribe(i -> log.info("{}", i), e -> log.warn("Error:", e));

        LazySubscriber<Integer> subscriber = new LazySubscriber<>();

        flux.subscribe(subscriber);

        //subscriber.request(5);
        subscriber.cancel();
    }

}

