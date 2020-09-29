package com.chrosciu.rx.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part03ContextInAction {
    public static void main(String[] args) throws Exception {
        Flux<Integer> data = Flux.just(3, 5, 7).concatWith(Flux.error(new RuntimeException("boom"))).publishOn(Schedulers.elastic());
        Flux<Integer> withLog = data.doOnEach(signal -> {
            String requestId = signal.getContext().getOrDefault("rid", "N/A");
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
        withLog.subscriberContext(ctx -> ctx.put("rid", "f00d")).subscribe();
        withLog.subscribe();

        Thread.sleep(1000);
    }
}
