package eu.chrost.rx.reactivestreams;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
class MySubscriber implements Subscriber<Long> {

    private Subscription subscription;

    public void request(long n) {
        if (subscription != null) {
            subscription.request(n);
        }
    }

    public void cancel() {
        if (subscription != null) {
            subscription.cancel();
            subscription = null;
        }
    }

    @Override
    public void onSubscribe(@NonNull Subscription subscription) {
        log.info("onSubscription: {}", subscription);
        if (this.subscription != null) {
            subscription.cancel();
            return;
        }
        this.subscription = subscription;
    }

    @Override
    public void onNext(@NonNull Long l) {
        log.info("onNext: {}", l);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        log.error("onError: ", throwable);
        subscription = null;
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
        subscription = null;
    }
}
