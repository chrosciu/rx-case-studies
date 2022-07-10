package com.chrosciu.rx.faq;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class NoErrorCallback {
    public static void main(String[] args) {
        Flux<String> f = Flux.error(new RuntimeException("Blah"));
        Flux<String> f2 = f.doOnError(e -> log.info("Error")).log();
        f2.subscribe();
        //doOnError callback is sufficient here, program will not terminate, even if no error callback is provided
        log.info("Reached");
    }
}
