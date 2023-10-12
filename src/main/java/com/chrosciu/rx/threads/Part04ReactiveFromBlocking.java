package com.chrosciu.rx.threads;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Part04ReactiveFromBlocking {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Flux<String> logins = Flux.defer(() -> Flux.fromIterable(getUserLogins()))
                .subscribeOn(Schedulers.boundedElastic());

        log.info("Before subscribe");

        logins.doFinally(st -> latch.countDown())
                .subscribe(s -> log.info(s),
                        e -> log.info("Error: ", e),
                        () -> log.info("Completed"));

        log.info("After subscribe");

        latch.await();

    }

    @SneakyThrows
    private static List<String> getUserLogins() {
        Thread.sleep(1000);
        log.info("Blocking !!!");
        return Arrays.asList("A", "B");
    }
}
