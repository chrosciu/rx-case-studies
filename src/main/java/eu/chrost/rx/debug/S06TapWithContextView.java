package eu.chrost.rx.debug;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.observability.DefaultSignalListener;
import reactor.core.observability.SignalListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.context.ContextView;

import java.util.UUID;
import java.util.function.Function;

@Slf4j
class S06TapWithContextView {
    @RequiredArgsConstructor
    private static class MySignalListener extends DefaultSignalListener<Integer> {
        private final String label;
        private final ContextView contextView;

        @Override
        public void doOnNext(Integer value) throws Throwable {
            log.info("[{}][{}] onNext: {}", label, getRequestId(), value);
        }

        @Override
        public void doFinally(SignalType terminationType) throws Throwable {
            log.info("[{}][{}] termination signal: {}", label, getRequestId(), terminationType);
        }

        private String getRequestId() {
            return contextView.getOrDefault("requestId", "N/A");
        }
    }

    public static void main(String[] args) {
        Mono<Integer> mono = badStream();
        mono
                //.contextWrite(ctx -> ctx.put("requestId", UUID.randomUUID().toString()))
                .subscribe(System.out::println, e -> log.warn("Error in stream: ", e));
    }

    private static Mono<Integer> badStream() {
        return Flux.range(0, 10)
                .map(i -> i * 2)
                .tap(contextView -> new MySignalListener("Before filter", contextView))
                .filter(i -> i % 3 != 0)
                .map(i -> i + 1)
                .elementAt(8)
                .filter(i -> i > 0);

    }
}
