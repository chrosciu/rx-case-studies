package eu.chrost.rx.threads;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part02PublishOn {
    public static void main(String[] args) {
        Scheduler scheduler = Schedulers.newBoundedElastic(10, 100, "Elastic");

        Flux<String> flux = Flux.<String>create(sink -> {
                log.info("Before feeding sink");
                sink.next("A");
                log.info("After sending A");
                sink.next("B");
                log.info("After sending B");
                sink.complete();
                log.info("After sending complete");
            })
            .log("Above publishOn")
            .publishOn(scheduler)
            .log("Below publishOn");

        log.info("Before subscribe");

        flux.subscribe(i -> log.info("Item: {}", i),
                e -> log.warn("Error: ", e),
                () -> log.info("Completed"));

        log.info("After subscribe");

        scheduler.dispose();
    }
}
