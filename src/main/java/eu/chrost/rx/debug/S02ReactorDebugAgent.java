package eu.chrost.rx.debug;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.tools.agent.ReactorDebugAgent;

@Slf4j
class S02ReactorDebugAgent {
    public static void main(String[] args) {
        ReactorDebugAgent.init();
        //required as our class is already loaded
        ReactorDebugAgent.processExistingClasses();
        Mono<Integer> mono = badStream();
        mono.subscribe(i -> log.info("Item: {}", i), e -> log.warn("Error in stream: ", e));
    }

    private static Mono<Integer> badStream() {
        return Flux.range(0, 10)
                .map(i -> i * 2)
                .filter(i -> i % 3 != 0)
                .map(i -> i + 1)
                .elementAt(8)
                .filter(i -> i > 0);
    }
}
