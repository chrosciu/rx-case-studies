package com.chrosciu.rx.flux;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

public class FluxGenerate {
    public static Publisher<Long> create(long to) {
        return Flux.generate(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return 1L;
            }
        }, new BiFunction<Long, SynchronousSink<Long>, Long>() {
            @Override
            public Long apply(Long context, SynchronousSink<Long> sink) {
                sink.next(context);
                if (context == to) {
                    sink.complete();
                }
                return context + 1;
            }
        });
    }
}
