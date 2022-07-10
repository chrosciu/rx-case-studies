package com.chrosciu.rx.faq;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoFromSupplier {
    public static void main(String[] args) {
        Mono<String> mono = Mono.fromSupplier(() -> {
            log.info("In supplier");
            return "A";
        });
        mono.subscribe();
        mono.subscribe();
    }
}
