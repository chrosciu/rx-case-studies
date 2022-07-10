package com.chrosciu.rx.faq;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class MonoWhen {
    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        Flux<Integer> flux1 = Flux.range(0, 3).delayElements(Duration.ofMillis(500)).log("[F1]");
        Flux<Integer> flux2 = Flux.range(0, 2).delayElements(Duration.ofMillis(400)).log("[F2]");
        Mono<Void> mono = Mono.when(flux1, flux2).doFinally(st -> latch.countDown()).log("[M]");
        mono.subscribe();
        latch.await();
    }
}
