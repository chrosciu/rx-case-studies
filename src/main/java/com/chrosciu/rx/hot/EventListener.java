package com.chrosciu.rx.hot;

public interface EventListener<T> {
    void next(T elem);
}
