package eu.chrost.rx.reactivestreams;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;

@Slf4j
public class MyReactorSubscriber extends BaseSubscriber<Long> {

    @Override
    public void hookOnNext(@NonNull Long l) {
        log.info("onNext: {}", l);
    }

    @Override
    public void hookOnError(@NonNull Throwable throwable) {
        log.error("onError: ", throwable);
    }

    @Override
    public void hookOnComplete() {
        log.info("onComplete");
    }
}
