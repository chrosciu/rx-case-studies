package eu.chrost.rx.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;


@Slf4j
public class Part02TaintedBusinessData {
    public static void main(String[] args) throws Exception {
        Flux<Integer> data = Flux.just(3, 5, 7).publishOn(Schedulers.boundedElastic());
        Flux<Tuple2<Integer, String>> tainted = data.map(i -> addRequestId(i,"f00d"));
        Flux<Tuple2<Integer, String>> logged = tainted.doOnNext(t -> log.info("RequestId: [{}] {}", t.getT2(), t.getT1()));
        logged.subscribe();

        Thread.sleep(1000);
    }

    private static Tuple2<Integer, String> addRequestId(Integer data, String requestId) {
        return Tuples.of(data, requestId);
    }
}
