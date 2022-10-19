package com.chrosciu.rx.reactivestreams;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class MyReactorGeneratedPublisher {
    public static Publisher<Long> create(long to) {
        return Flux.generate(() -> 1L, (context, sink) -> {
            sink.next(context);
            if (context == to) {
                sink.complete();
            }
            return context + 1;
        });
    }
}
