package com.chrosciu.rx.subscription;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@Slf4j
class MySubscriber<T> extends BaseSubscriber<T> {
    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        //NO-OP
    }

    @Override
    protected void hookOnNext(T value) {
        log.info("Item: {}", value);
    }

    @Override
    protected void hookFinally(SignalType type) {
        log.info("Finally: {}", type);
    }
}

@Slf4j
public class CompositeDispose {
    public static void main(String[] args) {
        Flux<String> flux1 = Flux.just("A", "B", "C").log("[1]");
        Flux<Integer> flux2 = Flux.just(1, 2, 3).log("[2]");

        MySubscriber<String> subscriber1 = new MySubscriber<>();
        MySubscriber<Integer> subscriber2 = new MySubscriber<>();

        Disposable.Composite disposable = Disposables.composite(subscriber1, subscriber2);

        flux1.subscribe(subscriber1);
        flux2.subscribe(subscriber2);

        subscriber1.request(1);
        subscriber2.request(1);

        //disposable.remove(subscriber2);
        disposable.dispose();
    }
}
