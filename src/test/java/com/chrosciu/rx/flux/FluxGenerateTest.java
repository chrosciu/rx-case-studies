package com.chrosciu.rx.flux;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

public class FluxGenerateTest extends PublisherVerification<Long> {
    public FluxGenerateTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long l) {
        return FluxGenerate.create(l);
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }
}
