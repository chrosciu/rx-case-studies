package com.chrosciu.rx.operators.flatmap;

import reactor.core.publisher.Flux;

class RemoteUserService {
    public Flux<User> fetchUsers() {
        return Flux.just(
                new User(null, "User1"),
                new User(null, "User2"),
                new User(null, "User3"),
                new User(null, "User4"),
                new User(null, "User5")
        ).log("fetchUsers");
    }
}
