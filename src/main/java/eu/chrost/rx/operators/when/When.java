package eu.chrost.rx.operators.when;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
class When {
    @SneakyThrows
    public static void main(String[] args) {
        Flux<Long> flux1 = Flux.interval(Duration.ofMillis(300)).take(3)
                .concatWith(Flux.error(new RuntimeException("Blah")))
                .log("flux1");

        Flux<String> flux2 = Flux.interval(Duration.ofMillis(200)).take(4)
                .map(String::valueOf)
                .log("flux2");

        Mono<Void> mono = Mono.when(flux1, flux2);

        mono.log("mono")
                .subscribe(
                        v -> log.info("Item: {}", v),
                        t -> log.info("Error: ", t),
                        () -> log.info("Completed")
                );

        Thread.sleep(1000);
    }
}
