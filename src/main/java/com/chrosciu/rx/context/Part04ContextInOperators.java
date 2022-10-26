package com.chrosciu.rx.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part04ContextInOperators {
    public static void main(String[] args) throws Exception {
        Flux<Integer> data = Flux.just(3, 5, 7)
                .concatWith(Flux.error(new RuntimeException("boom")))
                .publishOn(Schedulers.boundedElastic());
        Flux<Integer> withLog = data.flatMap(i -> Mono.deferContextual(contextView -> {
            String requestId = contextView.getOrDefault("rid", "N/A");
            log.info("RequestId: [{}] element: {}", requestId, i);
            return Mono.just(i);
        })).onErrorResume(t -> Mono.deferContextual(contextView -> {
            String requestId = contextView.getOrDefault("rid", "N/A");
            log.info("RequestId: [{}] error: ", requestId, t);
            return Mono.error(t);
        }));
        withLog.contextWrite(ctx -> ctx.put("rid", "f00d")).subscribe();
        //withLog.subscribe();

        Thread.sleep(1000);
    }
}
