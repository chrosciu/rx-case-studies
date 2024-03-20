package eu.chrost.rx.mono;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
public class Part01MonoStatic {
    public static void main(String[] args) {
        String s = "Marcin";
        Mono<String> mono = Mono.just(s);
        //Mono<String> mono = Mono.error(new RuntimeException("Blah"));
        //Mono<String> mono = Mono.empty();
        //Mono<String> mono = Mono.never();

        Disposable disposable = mono.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                log.info("Item: {}", s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                log.info("Error: ", throwable);
            }
        }, new Runnable() {
            @Override
            public void run() {
                log.info("Completed");
            }
        });

        log.info("{}", disposable.isDisposed());
        disposable.dispose();

        /* do not do this ! */
//        String result = mono.block();
//        log.info("{}", result);
    }
}
