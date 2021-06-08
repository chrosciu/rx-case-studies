package com.chrosciu.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

@Slf4j
public class Part01UnhandledError {
    public static void main(String[] args) {
        Flux<String> flux = Flux.just("A", "B");

        Flux<String> transformed = flux.map(t -> {
            try {
                throw new Exception("Baadf00d");
            } catch (Exception e) {
                throw Exceptions.propagate(e);
            }
        });
        //transformed.subscribe(System.out::println);


        transformed.subscribe(s -> System.out.println("item: " + s),
                e -> log.warn("Error in stream:", Exceptions.unwrap(e)));

    }
}
