package eu.chrost.rx.debug;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
class S03Checkpoint {
    public static void main(String[] args) {
        Mono<Integer> mono = badStream();
        mono.subscribe(i -> log.info("Item: {}", i), e -> log.warn("Error in stream: ", e));
    }

    private static Mono<Integer> badStream() {
        return Flux.range(0, 10)
                .map(i -> i * 2)
                .filter(i -> i % 3 != 0)
                .checkpoint("badStream() - Before second map()")
                .map(i -> i + 1)
                //.checkpoint()
                .checkpoint("badStream() - Before elementAt()")
                .elementAt(8)
                //.checkpoint()
                .checkpoint("badStream() - Before filter()")
                .filter(i -> i > 0)
                //.checkpoint();
                .checkpoint("badStream() - After filter()");
    }
}
