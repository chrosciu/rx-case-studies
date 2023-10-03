package com.chrosciu.rx.flux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

@Slf4j
public class Part02FluxGenerate {
    public static Flux<Long> create(long to) {
        return Flux.generate(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                log.info("In call");
                return 1L;
            }
        }, new BiFunction<Long, SynchronousSink<Long>, Long>() {
            @Override
            public Long apply(Long context, SynchronousSink<Long> sink) {
                log.info("In generator, context: {}", context);
                sink.next(context);
                if (context == to) {
                    sink.complete();
                }
                return context + 1;
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
