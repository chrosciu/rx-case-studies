package com.chrosciu.rx.operators.flatmap;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

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
        Flux<User> users = remoteUserService.fetchUsers()
                .flatMap(user -> localUserRepository.saveUser(user));

        users.doFinally(s -> countDownLatch.countDown())
                .subscribe(u -> log.info("Item: {}", u),
                        e -> log.warn("Error: ", e),
                        () -> log.info("Completed"));

        countDownLatch.await();
    }
}
