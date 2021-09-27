package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.tck.SubscriberBlackboxVerification;
import org.reactivestreams.tck.TestEnvironment;

public class MyReactorSubscriberTest extends SubscriberBlackboxVerification<Long> {
    public MyReactorSubscriberTest() {
        super(new TestEnvironment());
    }

    @Override
    public Subscriber<Long> createSubscriber() {
        return new MyReactorSubscriber();
    }

    @Override
    public Long createElement(int i) {
        return (long)i;
    }
}
