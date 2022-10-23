package com.chrosciu.rx.subscription;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@Slf4j
class SingleItemSubscriber<T> extends BaseSubscriber<T> {
    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        log.info("Item: {}", value);
    }

    @Override
    protected void hookOnComplete() {
        log.info("Completed");
    }

    @Override
    protected void hookFinally(SignalType type) {
        log.info("Finally : {}", type);
    }
}

public class BaseSubscriberExample {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 3).log();
        SingleItemSubscriber<Integer> subscriber = new SingleItemSubscriber<>();
        flux.subscribe(subscriber);
        subscriber.dispose();
    }
}
