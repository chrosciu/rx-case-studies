package com.chrosciu.rx.flux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

@Slf4j
public class Part03FluxCreate {
    public static Publisher<Long> create(long to) {
        return Flux.create(new Consumer<FluxSink<Long>>() {
            @Override
            public void accept(FluxSink<Long> sink) {
                for (long l = 1; l <= to; ++l) {
                    sink.next(l);
                }
                sink.complete();
            }
        });
    }
}
