package eu.chrost.rx.sinks;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;

@Slf4j
class LazyLoggingSubscriber<T> extends LoggingSubscriber<T> {

    public LazyLoggingSubscriber(String logPrefix) {
        super(logPrefix);
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        //request no items
    }
}
