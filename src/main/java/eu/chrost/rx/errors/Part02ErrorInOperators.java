package eu.chrost.rx.errors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Part02ErrorInOperators {
    private static Mono<Integer> divide(int i) {
        return Mono.fromCallable(() -> 12 / i)
                //.log("Before resume " + i)
                //.doOnError(e -> log.warn("Error in divide {}", i, e));
                //.onErrorResume(e -> Mono.empty());
                //.onErrorReturn(-1);
                .onErrorMap(e -> new RuntimeException(e));
                //.log("After resume " + i);
    }

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(2, 3, 0, 1)
                        .concatMap(i -> divide(i));
        flux.subscribe(s -> log.info("Item: {}", s),
                e -> log.warn("Error in stream: ", e),
                () -> log.info("Completed"));
    }
}
