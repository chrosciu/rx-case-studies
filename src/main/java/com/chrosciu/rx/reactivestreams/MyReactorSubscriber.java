package com.chrosciu.rx.reactivestreams;

import reactor.core.publisher.BaseSubscriber;

public class MyReactorSubscriber extends BaseSubscriber<Long> {
    @Override
    protected void hookOnError(Throwable throwable) {
        //NO-OP
    }
}
