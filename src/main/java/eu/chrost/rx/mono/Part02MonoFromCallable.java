package eu.chrost.rx.mono;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
public class Part02MonoFromCallable {

    private static String getName()  {
        log.info("Waiting...");
        //throw new RuntimeException("Blah");
        return "Marcin";
    }

    public static void main(String[] args) {
        log.info("Before creating mono");

        Mono<String> mono = Mono.fromCallable(() -> getName());

        log.info("Before subscribe");

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

        log.info("After subscribe");

        log.info("Is disposed: {}", disposable.isDisposed());
        disposable.dispose();
        log.info("Is disposed: {}", disposable.isDisposed());

        mono.subscribe(
                s -> log.info("Item: {}", s),
                throwable -> log.info("Error: ", throwable),
                () -> log.info("Completed")
        ).dispose();

    }

}
