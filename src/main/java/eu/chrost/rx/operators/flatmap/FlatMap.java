package eu.chrost.rx.operators.flatmap;

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
        new FlatMap().fetchUsersAndSaveThem();
    }

    @SneakyThrows
    private void fetchUsersAndSaveThem() {
        Flux<LocalUser> savedUsers = remoteUserService.fetchUsers()
                .flatMap(user -> localUserRepository.saveUser(user))
                .log("savedUsers");

        savedUsers.doFinally(s -> countDownLatch.countDown())
                .subscribe(u -> log.info("Item: {}", u),
                        e -> log.warn("Error: ", e),
                        () -> log.info("Completed"));

        countDownLatch.await();
    }
}
