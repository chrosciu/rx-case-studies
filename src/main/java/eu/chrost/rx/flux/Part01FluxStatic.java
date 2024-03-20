package eu.chrost.rx.flux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Slf4j
public class Part01FluxStatic {
    public static void main(String[] args) {
        String name1 = "Marcin";
        String name2 = "Tomasz";

        Flux<String> flux = Flux.just(name1, name2);
        //Flux<String> flux = Flux.error(new RuntimeException("Blah"));
//        Flux<String> flux = Flux.just(name1)
//                .concatWith(Flux.error(new RuntimeException("Blah")))
//                .concatWith(Flux.just(name2));

        Disposable disposable = flux.log().subscribe(s -> log.info("Item: {}", s),
                t -> log.info("Error: ", t),
                () -> log.info("Completed"));

        log.info("Is disposed: {}", disposable.isDisposed());
        disposable.dispose();
        log.info("Is disposed: {}", disposable.isDisposed());
    }
}
