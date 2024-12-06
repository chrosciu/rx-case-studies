package eu.chrost.rx.sinks;

import org.reactivestreams.Subscription;

class EagerLoggingSubscriber<T> extends LoggingSubscriber<T> {
    public EagerLoggingSubscriber(String logPrefix) {
        super(logPrefix);
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        request(Long.MAX_VALUE);
    }
}
