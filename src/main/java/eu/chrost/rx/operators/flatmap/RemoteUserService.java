package eu.chrost.rx.operators.flatmap;

import reactor.core.publisher.Flux;

class RemoteUserService {
    public Flux<RemoteUser> fetchUsers() {
        return Flux.just(
                new RemoteUser("User1"),
                new RemoteUser("User2"),
                new RemoteUser("User3"),
                new RemoteUser("User4"),
                new RemoteUser("User5")
        ).log("fetchUsers");
    }
}
