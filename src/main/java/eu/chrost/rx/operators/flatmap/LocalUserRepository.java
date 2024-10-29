package eu.chrost.rx.operators.flatmap;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
class LocalUserRepository {
    private static AtomicLong delayRatio = new AtomicLong(5L);
    private static int idSeq = 9376;

    public Mono<LocalUser> saveUser(RemoteUser user) {
        return Mono.fromCallable(() -> saveUserInternal(user))
                //.delayElement(Duration.ofMillis(400 * delayRatio.decrementAndGet())
                .doOnSubscribe(s -> log.info("Saving user: {}", user))
                .doOnNext(u -> log.info("User saved: {}", u))
                .log("saveUser")
                .subscribeOn(Schedulers.boundedElastic());

    }

    @SneakyThrows
    private LocalUser saveUserInternal(RemoteUser user) {
        log.info("saveUserInternal({})", user);
        Thread.sleep(400 * delayRatio.decrementAndGet());
        LocalUser savedUser = new LocalUser(idSeq++, user.name());
        log.info("saveUserInternal({}) -> {}", user, savedUser);
        return savedUser;
    }
}
