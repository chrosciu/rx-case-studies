package eu.chrost.rx.sinks;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.tools.shaded.net.bytebuddy.build.Plugin;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
class EmittingTask implements Runnable {
    private final int initialValue;
    private final int numberOfValuesToEmit;
    private final Sinks.Many<Integer> sink;

    @Override
    public void run() {
        for (int i = initialValue; i < initialValue + numberOfValuesToEmit; ++i) {
            var emitResult = sink.tryEmitNext(i);
            if (emitResult != Sinks.EmitResult.OK) {
                log.info("Emit result for {}: {}", i, emitResult);
            }

//            sink.emitNext(i, Sinks.EmitFailureHandler.FAIL_FAST);
//
//            sink.emitNext(i, Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1)));
        }
    }
}

@Slf4j
class S03ParallelEmission {
    @SneakyThrows
    public static void main(String[] args) {
        Sinks.Many<Integer> sink = Sinks.many().replay().all();
        Flux<Integer> flux = sink.asFlux();

        Thread t1 = new Thread(new EmittingTask(0, 50, sink));
        Thread t2 = new Thread(new EmittingTask(51, 50, sink));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        flux.subscribe(s -> log.info("{}", s), e -> log.warn("", e), () -> log.info("completed"));
    }
}
