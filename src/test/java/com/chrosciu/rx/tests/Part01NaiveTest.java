package com.chrosciu.rx.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part01NaiveTest {

    @Test
    public void simpleFluxTest() {
        //given
        Flux<String> flux = simpleFlux();

        //when
        Stream<String> iterable = flux.toStream();
        List<String> list = iterable.collect(Collectors.toList());

        //then
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(0));
    }

    private Flux<String> simpleFlux() {
        return Flux.just("A", "B");
    }
}
