package eu.chrost.rx.debug;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.observability.DefaultSignalListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

@Slf4j
class S05Tap {

    @RequiredArgsConstructor
    private static class MySignalListener extends DefaultSignalListener<Integer> {
        private final String label;

        @Override
        public void doOnNext(Integer value) throws Throwable {
            log.info("[{}] onNext: {}", label, value);
        }

        @Override
        public void doFinally(SignalType terminationType) throws Throwable {
            log.info("[{}] termination signal: {}", label, terminationType);
        }
    }

    public static void main(String[] args) {
        Mono<Integer> mono = badStream();
        mono.subscribe(System.out::println, e -> log.warn("Error in stream: ", e));
    }

    private static Mono<Integer> badStream() {
        return Flux.range(0, 10)
                .map(i -> i * 2)
                .tap(() -> new MySignalListener("Before filter"))
                .filter(i -> i % 3 != 0)
                .tap(() -> new MySignalListener("After filter"))
                .map(i -> i + 1)
                .tap(() -> new MySignalListener("Before elementAt"))
                .elementAt(8)
                .tap(() -> new MySignalListener("After elementAt"))
                .filter(i -> i > 0);
    }
}
