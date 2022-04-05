package com.chrosciu.rx.hot;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

@RequiredArgsConstructor
@Slf4j
public class SimpleSubscriber<T> extends BaseSubscriber<T> {
    private final String prefix;

    @Override
    public void hookOnSubscribe(@NonNull Subscription s) {
        //intentionally no-op
    }

    @Override
    public void hookOnNext(@NonNull T elem) {
        log.info("{}: {}", prefix, elem);
    }

    @Override
    public void hookOnError(@NonNull Throwable throwable) {
        log.error(prefix, throwable);
    }

    @Override
    public void hookOnComplete() {
        log.info("{}: completed", prefix);
    }
}
