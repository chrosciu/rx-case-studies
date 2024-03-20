package eu.chrost.rx.async;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@RequiredArgsConstructor
class Booking implements Runnable {
    @NonNull private final String type;
    @NonNull private final String reservation;
    @NonNull private final Consumer<String> callback;
    @NonNull private final Consumer<Throwable> errorCallback;

    @SneakyThrows
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            if ("flight".equals(type)) {
                throw new RuntimeException("flight");
            }
            callback.accept(String.format("Booked %s for reservation %s", type, reservation));
        } catch (Throwable t) {
            errorCallback.accept(t);
        }
    }
}

@Slf4j
public class Part01Callbacks {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        StringBuilder results = new StringBuilder();
        String reservation = "baadf00d";
        Booking flightBooking = new Booking("flight", reservation, results::append, e -> log.error("", e));
        Booking hotelBooking = new Booking("hotel", reservation, results::append, e -> log.error("", e));

        executorService.submit(flightBooking);
        executorService.submit(hotelBooking);

        executorService.awaitTermination(3, TimeUnit.SECONDS);
        executorService.shutdown();

        log.info(results.toString());

    }
}
