package com.chrosciu.rx.reactivestreams;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
@RequiredArgsConstructor
class MyPublisher implements Publisher<Long> {
    private final long n;
    @Getter
    private boolean terminated = false;

    private MySubscription subscription;

    class MySubscription implements Subscription {
        private final Subscriber<? super Long> subscriber;
        private long demand;
        private long next;
        private boolean inRequest;

        private MySubscription(Subscriber<? super Long> subscriber) {
            this.subscriber = subscriber;
            this.demand = 0;
            this.next = 1;
            this.inRequest = false;
        }

        @Override
        public void request(long l) {
            if (isTerminated()) {
                return;
            }
            if (l <= 0) {
                this.subscriber.onError(new IllegalArgumentException());
                terminate();
            }
            this.demand += l;
            if (this.inRequest) {
                return;
            }
            this.inRequest = true;
            while (this.demand > 0 && this.next <= MyPublisher.this.n) {
                this.subscriber.onNext(this.next);
                if (this.next == MyPublisher.this.n) {
                    this.subscriber.onComplete();
                    terminate();
                }
                this.next += 1;
                this.demand -= 1;
            }
            this.inRequest = false;
        }

        @Override
        public void cancel() {
            terminate();
        }
    }

    static class MyDummySubscription implements Subscription {
        @Override
        public void request(long n) {
            return;
        }

        @Override
        public void cancel() {
            return;
        }
    }

    private final Subscription dummySubscription = new MyDummySubscription();

    @Override
    public void subscribe(@NonNull Subscriber<? super Long> subscriber) {
        if (isTerminated()) {
            subscriber.onSubscribe(dummySubscription);
            subscriber.onError(new IllegalStateException("Cannot subscribe to terminated publisher"));
            return;
        }
        if (this.subscription != null) {
            subscriber.onSubscribe(dummySubscription);
            subscriber.onError(new IllegalStateException("Cannot subscribe more than once"));
            return;
        }
        this.subscription = new MySubscription(subscriber);
        subscriber.onSubscribe(this.subscription);
    }

    private void terminate() {
        this.subscription = null;
        this.terminated = true;
    }
}
