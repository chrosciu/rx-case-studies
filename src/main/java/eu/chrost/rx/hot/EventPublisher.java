package eu.chrost.rx.hot;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventPublisher<T> {
    private EventListener<? super T> eventListener;

    public void registerEventListener(@NonNull EventListener<? super T> eventListener) {
        log.info("Registering event listener");
        if (this.eventListener != null) {
            throw new IllegalStateException();
        }
        this.eventListener = eventListener;
        log.info("Registered event listener");
    }

    public void emit(@NonNull T elem) {
        if (eventListener != null) {
            log.info("Emitting: {}", elem);
            eventListener.next(elem);
            log.info("Emitted: {}", elem);
        } else {
            log.info("Dropped: {}", elem);
        }
    }
}
