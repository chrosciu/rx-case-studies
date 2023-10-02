package com.chrosciu.rx.mono;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class Part02MonoFromCallable {

    private static String getName()  {
        log.info("Waiting...");
        //throw new RuntimeException("Blah");
        return "Marcin";
    }

    public static void main(String[] args) {
        log.info("Before creating mono");

        Mono<String> mono = Mono.fromCallable(() -> getName());

        log.info("Before subscribe");

        Disposable disposable = mono.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                log.info("Item: {}", s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                log.info("Error: ", throwable);
            }
        }, new Runnable() {
            @Override
            public void run() {
                log.info("Completed");
            }
        });

        log.info("After subscribe");

        log.info("{}", disposable.isDisposed());
        disposable.dispose();

    }

}
