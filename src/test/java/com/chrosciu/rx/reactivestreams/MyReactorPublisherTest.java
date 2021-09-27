package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

public class MyReactorPublisherTest extends PublisherVerification<Long> {
    public MyReactorPublisherTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long l) {
        return MyReactorPublisher.create(1, l);
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }
}
