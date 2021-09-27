package com.chrosciu.rx.reactivestreams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class SubscriptionExample {
    public static void main(String[] args) {
        new SubscriptionExample().run();
    }

    private void run() {
        Publisher<Long> publisher = new MyPublisher(100);
        Subscriber<Long> subscriber = new MySubscriber();
        log.info("subscribe()");
        publisher.subscribe(subscriber);
        log.info("request(5)");
        ((MySubscriber)subscriber).request(5);
        //log.info("cancel()");
        //((MySubscriber)subscriber).cancel();
        log.info("request(5)");
        ((MySubscriber)subscriber).request(5);
        log.info("request(15)");
        ((MySubscriber)subscriber).request(15);
    }
}
