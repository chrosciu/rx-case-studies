package eu.chrost.rx.hot;

public interface EventListener<T> {
    void next(T elem);
}
