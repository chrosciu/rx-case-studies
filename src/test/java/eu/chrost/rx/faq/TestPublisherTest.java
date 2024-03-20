package eu.chrost.rx.faq;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class TestPublisherTest {
    @Test
    public void test() {
        TestPublisher<String> testPublisher = TestPublisher.create();
        StepVerifier.create(testPublisher)
                .then(() -> testPublisher.next("A"))
                .expectNext("A")
                //if uncommented will block undefinitely as only one element is emitted up to this point
                //.expectNext("B")
                .then(() -> testPublisher.emit("B"))
                .expectNext("B")
                .verifyComplete();
    }


}
