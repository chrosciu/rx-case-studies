package eu.chrost.rx.sinks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

@RequiredArgsConstructor
@Slf4j
abstract class LoggingSubscriber<T> extends BaseSubscriber<T> {
    protected final String logPrefix;

    @Override
    protected abstract void hookOnSubscribe(Subscription subscription);

    @Override
    protected void hookOnNext(T value) {
        log.info("[{}] onNext: {}", logPrefix, value);
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        log.warn("[{}] onError: ", logPrefix, throwable);
    }

    @Override
    protected void hookOnComplete() {
        log.info("[{}] onComplete", logPrefix);
    }

    @Override
    protected void hookOnCancel() {
        log.info("[{}] onCancel", logPrefix);
    }
}
