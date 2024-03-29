package eu.chrost.rx.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part03ContextInAction {
    public static void main(String[] args) throws Exception {
        Flux<Integer> data = Flux.just(3, 5, 7)
                .concatWith(Flux.error(new RuntimeException("boom")))
                .publishOn(Schedulers.boundedElastic());
        Flux<Integer> withLog = data.doOnEach(signal -> {
            String requestId = signal.getContextView().getOrDefault("rid", "N/A");
            switch(signal.getType()) {
                case ON_NEXT:
                    log.info("RequestId: [{}] element: {}", requestId, signal.get());
                    break;
                case ON_ERROR:
                    log.info("RequestId: [{}] error: ", requestId, signal.getThrowable());
                    break;
                case ON_COMPLETE:
                    log.info("RequestId: [{}] stream completed", requestId);
                    break;
            }
        });
        withLog.contextWrite(ctx -> ctx.put("rid", "f00d")).subscribe();
        //withLog.subscribe();

        Thread.sleep(1000);
    }
}
