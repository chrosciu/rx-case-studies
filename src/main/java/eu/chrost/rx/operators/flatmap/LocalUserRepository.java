package eu.chrost.rx.operators.flatmap;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Random;

@Slf4j
class LocalUserRepository {
    private static Random random = new Random();
    private static int idSeq = random.nextInt(10000);

    public Mono<User> saveUser(User user) {
        return Mono.fromCallable(() -> saveUserInternal(user))
                //.delayElement(Duration.ofMillis(random.nextInt(1000)))
                .doOnSubscribe(s -> log.info("Saving user: {}", user))
                .doOnNext(u -> log.info("User saved: {}", u))
                .log("saveUser");
                //.subscribeOn(Schedulers.boundedElastic())

    }

    @SneakyThrows
    private User saveUserInternal(User user) {
        log.info("saveUserInternal({})", user);
        //Thread.sleep(random.nextInt(1000));
        User savedUser = user.withId(idSeq++);
        log.info("saveUserInternal({}) -> {}", user, savedUser);
        return savedUser;
    }
}
