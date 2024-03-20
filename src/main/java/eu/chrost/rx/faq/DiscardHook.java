package eu.chrost.rx.faq;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

@Slf4j
@Getter
class Resource {
    private final int number;

    public Resource(int number) {
        this.number = number;
        log.info("Creating: {}", number);
    }

    public void release() {
        log.info("Releasing: {}", number);
    }
}

@Slf4j
public class DiscardHook {
    public static void main(String[] args) {
        Flux<Resource> resources = Flux.create(sink -> {
            IntStream.range(0, 2).forEach(i -> sink.next(new Resource(i)));
        });
        //Flux<Resource> filtered = resources.filter(r -> r.getNumber() % 2 == 0);
        Flux<Resource> filtered = resources
                .filter(r -> r.getNumber() % 2 == 0)
                .doOnDiscard(Resource.class, r -> r.release());

        filtered.subscribe(r -> {
            log.info("Resource: {}", r.getNumber());
            r.release();
        }, e -> log.warn("Error: ", e));
    }
}
