package com.chrosciu.rx.reactivestreams;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
class MyPublisher implements Publisher<Long> {

    private Subscriber<? super Long> subscriber;
    private MySubscription subscription;
    private long n;
    private long demand;
    private long next;
    private boolean inRequest;

    public MyPublisher(long n) {
        this.n = n;
    }

    class MySubscription implements Subscription {

        @Override
        public void request(long l) {
            if (MyPublisher.this.subscriber == null) {
                return;
            }
            if (l <= 0) {
                MyPublisher.this.subscriber.onError(new IllegalArgumentException());
                MyPublisher.this.subscriber = null;
            }
            MyPublisher.this.demand += l;
            if (MyPublisher.this.inRequest) {
                return;
            }
            MyPublisher.this.inRequest = true;
            while (MyPublisher.this.demand > 0 && MyPublisher.this.next <= MyPublisher.this.n) {
                MyPublisher.this.subscriber.onNext(MyPublisher.this.next);
                if (MyPublisher.this.next == MyPublisher.this.n) {
                    MyPublisher.this.subscriber.onComplete();
                    MyPublisher.this.subscriber = null;
                }
                MyPublisher.this.next += 1;
                MyPublisher.this.demand -= 1;
            }
            MyPublisher.this.inRequest = false;
        }

        @Override
        public void cancel() {
            MyPublisher.this.subscriber = null;
        }
    }

    @Override
    public void subscribe(@NonNull Subscriber<? super Long> subscriber) {
        if (this.subscriber != null) {
            throw new IllegalStateException("Cannot subscribe more than once");
        }
        this.subscriber = subscriber;
        this.demand = 0;
        this.next = 1;
        this.inRequest = false;
        this.subscription = new MySubscription();
        this.subscriber.onSubscribe(subscription);
    }
}
