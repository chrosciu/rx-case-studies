package eu.chrost.rx.operators.then;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class Then {
    @SneakyThrows
    public static void main(String[] args) {
        Flux<Long> flux = Flux.interval(Duration.ofMillis(300)).take(3)
                .concatWith(Flux.error(new RuntimeException("Blah")))
                .log("flux");

        Mono<Void> mono = flux.then();

        mono.log("mono")
                .subscribe(
                        v -> log.info("Item: {}", v),
                        t -> log.info("Error: ", t),
                        () -> log.info("Completed")
                );

        Thread.sleep(1000);
    }
}
