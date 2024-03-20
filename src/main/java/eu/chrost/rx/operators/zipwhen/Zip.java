package eu.chrost.rx.operators.zipwhen;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Slf4j
public class Zip {
    public static void main(String[] args) {
        Mono<Tuple2<String, String>> ticket = bookTicket("Katowice").log("[0]");
        ticket.subscribe(i -> log.info("Item: {}", i),
                e -> log.warn("Error: ", e));
    }

    private static Mono<Tuple2<String, String>> bookTicket(String destination) {
        return bookTicketThere(destination).zipWith(
                bookTicketBack(destination)
        );
    }

    private static Mono<String> bookTicketThere(String destination) {
        return Mono.fromCallable(() -> String.format("Booked ticket to %s", destination)).log("[1]");
    }

    private static Mono<String> bookTicketBack(String destination) {
        return Mono.fromCallable(() -> String.format("Booked ticket from %s", destination)).log("[2]");
    }
}
