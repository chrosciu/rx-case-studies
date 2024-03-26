package eu.chrost.rx.debug;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
class S04Log {
    public static void main(String[] args) {
        Mono<Integer> mono = badStream();
        mono.subscribe(System.out::println, e -> log.warn("Error in stream: ", e));
    }

    private static Mono<Integer> badStream() {
        return Flux.range(0, 10)
                .map(i -> i * 2)
                .log("Before filter ")
                .filter(i -> i % 3 != 0)
                .log("After filter ")
                .map(i -> i + 1)
                .log("After map ")
                .elementAt(8)
                .log("After elementAt ")
                .filter(i -> i > 0);
    }
}
