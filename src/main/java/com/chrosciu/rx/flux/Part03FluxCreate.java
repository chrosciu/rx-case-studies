package com.chrosciu.rx.flux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

@Slf4j
public class Part03FluxCreate {
    private static Flux<Long> create(long to) {
        return Flux.create(new Consumer<FluxSink<Long>>() {
            @Override
            public void accept(FluxSink<Long> sink) {
                log.info("In sink");
                for (long l = 1; l <= to; ++l) {
                    sink.next(l);
                }
                //sink.error(new RuntimeException("Blah!"));
                sink.complete();
            }
        });
    }

    public static void main(String[] args) {

        log.info("Before create");

        Flux<Long> flux = create(5L);

        log.info("Before subscribe");

        Disposable disposable = flux.subscribe(l -> log.info("Item: {}", l),
                t -> log.info("Error: ", t),
                () -> log.info("Completed"));

        log.info("After subscribe");

        log.info("{}", disposable.isDisposed());
        disposable.dispose();
    }
}
