package com.chrosciu.rx.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Divisors {
    public static List<Integer> getDivisors(Integer number) {
        if ((null == number) || (number <= 0)) {
            throw new IllegalArgumentException("Bad number");
        }
        List<Integer> divisors = new ArrayList<>();
        divisors.add(1);
        for (int i = 2; i < number; ++i) {
            if (number % i == 0) {
                divisors.add(i);
            }
        }
        divisors.add(number);
        return divisors;
    }

    public static void main(String[] args) {
        List<Integer> divisors = getDivisors(18);
        System.out.println(Arrays.toString(divisors.toArray()));
    }
}
