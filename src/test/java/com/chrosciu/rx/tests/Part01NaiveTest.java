package com.chrosciu.rx.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Part01NaiveTest {

    @Test
    public void simpleFluxTest() {
        //given
        Flux<String> flux = simpleFlux();

        //when
        Stream<String> iterable = flux.toStream();
        List<String> list = iterable.collect(Collectors.toList());

        //then
        assertThat(list).containsExactly("A", "B");
    }

    private Flux<String> simpleFlux() {
        return Flux.just("A", "B");
    }
}
