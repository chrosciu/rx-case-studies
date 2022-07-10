package com.chrosciu.rx.faq;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoVoid {
    public static void main(String[] args) {
        Flux<String> flux = Flux.just("A", "B").log("[F]");
        Mono<Void> monoVoid = flux.then();
        monoVoid.log("[M]").subscribe();
    }
}
