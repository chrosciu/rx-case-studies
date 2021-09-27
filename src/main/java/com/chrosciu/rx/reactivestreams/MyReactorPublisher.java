package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class MyReactorPublisher {
    public static Publisher<Long> create(long from, long to) {
        return Flux.range((int)from, (int)(to - from + 1)).map(i -> (long)i);
    }
}
