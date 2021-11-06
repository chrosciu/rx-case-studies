package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

public class MyReactorGeneratedPublisherTest extends PublisherVerification<Long> {
    public MyReactorGeneratedPublisherTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long l) {
        return MyReactorGeneratedPublisher.create(l);
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }
}
