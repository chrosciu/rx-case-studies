package com.chrosciu.rx.flux;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

public class Part02FluxGenerateTest extends PublisherVerification<Long> {
    public Part02FluxGenerateTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long l) {
        return Part02FluxGenerate.create(l);
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }
}
