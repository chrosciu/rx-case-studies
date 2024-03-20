package eu.chrost.rx.reactivestreams;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

@Slf4j
public class SubscriptionExample {
    public static void main(String[] args) {
        new SubscriptionExample().run();
    }

    private void run() {
        Publisher<Long> publisher = new MyPublisher(100);
        MySubscriber subscriber = new MySubscriber();
        log.info("subscribe()");
        publisher.subscribe(subscriber);
//        log.info("cancel()");
//        subscriber.cancel();
        log.info("request(5)");
        subscriber.request(5);
//        log.info("request(200)");
//        subscriber.request(200);
        log.info("cancel()");
        subscriber.cancel();
    }
}
