package eu.chrost.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Part01UnhandledError {
    public static void main(String[] args) {
        Flux<String> flux = Flux
                .just("Marcin", "Tomasz")
                .concatWith(Flux.error(new RuntimeException("Blah!")));

        flux.subscribe(i -> log.info("Item: {}", i));

//        flux.subscribe(i -> log.info("Item: {}", i),
//                e -> log.warn("Error: ", e),
//                () -> log.info("Completed"));

//        flux.subscribe(i -> log.info("Item: {}", i),
//                e -> {},
//                () -> log.info("Completed"));

        log.info("Terminated normally");
    }
}
