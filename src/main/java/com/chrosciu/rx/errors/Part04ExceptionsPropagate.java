package com.chrosciu.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Slf4j
public class Part04ExceptionsPropagate {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(i -> {
                    try {
                        return transform(i);
                    } catch (IOException e) {
                        //throw new RuntimeException(e);
                        throw Exceptions.propagate(e);
                    }
                });
        flux.subscribe(i -> log.info("Item: {}", i),
                e -> log.warn("Error: ", e));
    }


    private static int transform(int i) throws IOException {
        if (3 == i) {
            throw new IOException("blah");
        }
        return i + 1;
    }
}
