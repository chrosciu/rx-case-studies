package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class MyReactorPublisher {
    public static Publisher<Long> create(long to) {
        return Flux.range(1, (int)to).map(i -> (long)i);
    }
}
