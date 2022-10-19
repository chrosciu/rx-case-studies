package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class MyReactorCreatedPublisher {
    public static Publisher<Long> create(long to) {
        return Flux.create(sink -> {
            for (long l = 1; l <= to; ++l) {
                sink.next(l);
            }
            sink.complete();
        });
    }
}
