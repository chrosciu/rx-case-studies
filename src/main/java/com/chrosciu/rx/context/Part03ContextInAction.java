package com.chrosciu.rx.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Part03ContextInAction {
    public static void main(String[] args) throws Exception {
        Flux<Integer> data = Flux.just(3, 5, 7).publishOn(Schedulers.elastic());
        Flux<Integer> withLog = data
                .flatMap(i -> Mono.subscriberContext()
                        .doOnNext(ctx -> log.info("RequestId: [{}] {}", ctx.getOrEmpty("rid").orElse("N/A"), i))
                        .map(ctx -> i));
        withLog.subscriberContext(ctx -> ctx.put("rid", "f00d")).subscribe();
        withLog.subscribe();

        Thread.sleep(1000);
    }
}
