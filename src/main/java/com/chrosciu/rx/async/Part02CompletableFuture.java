package com.chrosciu.rx.async;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
class BookingC implements Callable<String> {
    @NonNull
    private final String type;
    @NonNull
    private final String reservation;

    @Override
    @SneakyThrows
    public String call() {
        Thread.sleep(1000);
        log.info("{}", type);
        if ("flight".equals(type)) {
            throw new RuntimeException("flight");
        }
        return String.format("Booked %s for reservation %s", type, reservation);
    }
}

@Slf4j
public class Part02CompletableFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String reservation = "baadf00d";
        BookingC flightBooking = new BookingC("flight", reservation);
        BookingC hotelBooking = new BookingC("hotel", reservation);

        CompletableFuture<String> flight = CompletableFuture.supplyAsync(flightBooking::call, executorService)
                .handle((r, t) -> r != null ? r : "Flight booking error");
        CompletableFuture<String> hotel = CompletableFuture.supplyAsync(hotelBooking::call, executorService)
                .handle((r, t) -> r != null ? r : "Hotel booking error");

        String result = Stream.of(flight, hotel)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));

        log.info(result);

        executorService.shutdown();


    }
}
