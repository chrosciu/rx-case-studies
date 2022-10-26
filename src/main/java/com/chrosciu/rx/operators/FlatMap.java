package com.chrosciu.rx.operators;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@Value
@With
class User {
    Integer id;
    String name;
}

class RemoteUserService {
    Flux<User> fetchUsers() {
        return Flux.just(
                new User(null, "User1"),
                new User(null, "User2"),
                new User(null, "User3"),
                new User(null, "User4"),
                new User(null, "User5"),
                new User(null, "User6"),
                new User(null, "User7"),
                new User(null, "User8"),
                new User(null, "User9"),
                new User(null, "User10")
                );
    }
}

class LocalUserRepository {
    private static int idSeq = new Random().nextInt(10000);

    Mono<User> saveUser(User user) {
        return Mono.fromCallable(() -> user.withId(idSeq++)).delayElement(Duration.ofMillis(new Random().nextInt(1000)));
    }
}

@Slf4j
public class FlatMap {
    RemoteUserService remoteUserService = new RemoteUserService();
    LocalUserRepository localUserRepository = new LocalUserRepository();
    CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        new FlatMap().processUsers();
    }

    @SneakyThrows
    private void processUsers() {
        Flux<User> users = remoteUserService.fetchUsers().switchMap(localUserRepository::saveUser);

        users.doFinally(s -> countDownLatch.countDown()).subscribe(u -> log.info("{}", u));

        countDownLatch.await();
    }
}
