package com.chrosciu.rx.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part01NaiveTest {

    @Test
    public void simpleFluxTest() {
        //given
        Flux<String> flux = Flux.just("A", "B");

        //when
        Stream<String> iterable = flux.toStream();
        List<String> list = iterable.collect(Collectors.toList());

        //then
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("A", list.get(0));



    }

}
