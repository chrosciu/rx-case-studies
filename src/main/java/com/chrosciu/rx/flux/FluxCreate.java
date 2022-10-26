package com.chrosciu.rx.flux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxCreate {
    public static Publisher<Long> create(long to) {
        return Flux.create(sink -> {
            for (long l = 1; l <= to; ++l) {
                sink.next(l);
            }
            sink.complete();
        });
    }
}
