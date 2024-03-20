package eu.chrost.rx.flux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Part04FluxInterval {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("Before create");

        CountDownLatch latch = new CountDownLatch(1);

        Flux<Long> flux = Flux
                .interval(Duration.ofMillis(100))
                .take(10);

        log.info("Before subscribe");

        Disposable disposable = flux.log().subscribe(l -> log.info("Item: {}", l),
                t -> log.info("Error: ", t),
                () -> {
                    log.info("Completed");
                    latch.countDown();
                });

        log.info("After subscribe");

        latch.await();

        disposable.dispose();
    }
}
