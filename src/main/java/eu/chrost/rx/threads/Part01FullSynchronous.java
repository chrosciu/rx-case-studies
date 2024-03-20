package eu.chrost.rx.threads;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Part01FullSynchronous {
    @SneakyThrows
    public static void main(String[] args) {
        Flux<String> flux = Flux.<String>create(sink -> {
            log.info("Before feeding sink");
            sink.next("A");
            log.info("After sending A");
            sink.next("B");
            log.info("After sending B");
            sink.complete();
            log.info("After feeding sink");
        }).log();

        log.info("Before subscribe");

        flux.subscribe(i -> log.info("Item: {}", i),
                e -> log.warn("Error: ", e),
                () -> log.info("Completed"));

        log.info("After subscribe");
    }
}
