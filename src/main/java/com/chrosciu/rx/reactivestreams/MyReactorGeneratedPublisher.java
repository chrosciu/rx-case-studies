package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class MyReactorGeneratedPublisher {
    public static Publisher<Long> create(long to) {
        return Flux.generate(() -> 1L, (context, longSynchronousSink) -> {
            longSynchronousSink.next(context);
            if (context == to) {
                longSynchronousSink.complete();
            }
            return context + 1;
        });
    }
}
