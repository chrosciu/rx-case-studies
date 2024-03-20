package eu.chrost.rx.operators.map;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Map {
    public static void main(String[] args) {
        Flux<String> upstream = Flux.just("Marcin", "Tomasz", "Pawel").log("Upstream");
        //.... map
        Flux<String> downstream = upstream.map(s -> transform(s)).log("Downstream");
        //.... subscriber

        downstream.subscribe(s -> log.info("Item: {}", s),
                t -> log.info("Error: ", t),
                () -> log.info("Completed"));
    }

    private static String transform(String s) {
        if ("Tomasz".equals(s)) {
            throw new RuntimeException("Blah");
        }
        return s.toLowerCase();
    }
}
