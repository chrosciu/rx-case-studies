package eu.chrost.rx.faq;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoDefer {
    public static void main(String[] args) {
        Mono<String> mono = Mono.just("A").log();
        Mono<String> deferred = Mono.defer(() ->  {
            log.info("In supplier");
            return mono;
        });
        deferred.subscribe();
        deferred.subscribe();
    }
}
