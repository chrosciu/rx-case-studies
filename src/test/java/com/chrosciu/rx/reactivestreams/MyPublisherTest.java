package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

public class MyPublisherTest extends PublisherVerification<Long> {
    public MyPublisherTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long l) {
        return new MyPublisher(l);
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }
}
