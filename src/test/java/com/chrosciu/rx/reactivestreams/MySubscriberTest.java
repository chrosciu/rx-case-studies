package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.tck.SubscriberBlackboxVerification;
import org.reactivestreams.tck.TestEnvironment;

public class MySubscriberTest extends SubscriberBlackboxVerification<Long> {
    public MySubscriberTest() {
        super(new TestEnvironment());
    }

    @Override
    public Subscriber<Long> createSubscriber() {
        return new MySubscriber();
    }

    @Override
    public Long createElement(int i) {
        return (long)i;
    }

    @Override
    public void triggerRequest(final Subscriber<? super Long> subscriber) {
        ((MySubscriber)subscriber).request(1);
    }
}
