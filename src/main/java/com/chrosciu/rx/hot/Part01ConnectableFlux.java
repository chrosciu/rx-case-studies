package com.chrosciu.rx.hot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@Getter
@Setter
class SubscriptionWrapper {
    Subscription subscription;
}

@Slf4j
public class Part01ConnectableFlux {
    public static void main(String[] args) throws Exception {
        Flux<String> original = Flux.just("A", "B", "C", "D").log("original");
        ConnectableFlux<String> connectable = original.publish();
        //ConnectableFlux<String> connectable = original.replay();

        SubscriptionWrapper sw1 = new SubscriptionWrapper();
        SubscriptionWrapper sw2 = new SubscriptionWrapper();

        connectable.subscribe(s -> log.info("F1: {}", s), e -> {}, () -> {}, s1 -> {sw1.setSubscription(s1);});
        connectable.subscribe(s -> log.info("F2: {}", s), e -> {}, () -> {}, s2 -> {sw2.setSubscription(s2);});

        log.info("done subscribing");
        Thread.sleep(500);

        log.info("initial requests");
        sw1.getSubscription().request(3);
        sw2.getSubscription().request(2);

        Thread.sleep(500);

        log.info("will now connect");
        connectable.connect();

        Thread.sleep(500);
        log.info("further requests");

        sw1.getSubscription().request(3);
        sw2.getSubscription().request(2);

    }
}
