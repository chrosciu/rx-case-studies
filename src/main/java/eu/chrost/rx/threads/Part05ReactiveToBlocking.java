package eu.chrost.rx.threads;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Part05ReactiveToBlocking {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Flux<String> logins = Flux.just("A", "B")
                .publishOn(Schedulers.boundedElastic());

        logins.doFinally(st -> latch.countDown())
                .doOnNext(u -> save(u))
                .subscribe(s -> log.info(s),
                        e -> log.info("Error: ", e),
                        () -> log.info("Completed"));

        latch.await();
    }

    @SneakyThrows
    private static void save(String login) {
        //blocks for long time
        Thread.sleep(1000);
        log.info("Blocking save !!! for " + login);
    }
}
