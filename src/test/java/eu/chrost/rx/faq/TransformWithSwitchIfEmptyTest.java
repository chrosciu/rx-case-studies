package eu.chrost.rx.faq;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;
import java.util.function.Supplier;

public class TransformWithSwitchIfEmptyTest {
    @Test
    void transform() {
        Mono.defer(monoSource)
                .transform(transformation)
                .as(StepVerifier::create)
                .expectNext("bad")
                .verifyComplete();
    }

    private Supplier<Mono<String>> monoSource = () -> {
        //will be called only once, as we use cache() operator
        System.out.println("creating");
        return Mono.just("bad");
    };

    private Function<Mono<String>, Mono<String>> transformation = stringMono -> {
        Mono<String> cached = stringMono.cache();
        return cached
                .filter(s -> s.equals("good"))
                .flatMap(s -> Mono.<String>error(new RuntimeException()))
                //switch to itself -> cache to prevent double subscription
                .switchIfEmpty(cached);
    };
}
