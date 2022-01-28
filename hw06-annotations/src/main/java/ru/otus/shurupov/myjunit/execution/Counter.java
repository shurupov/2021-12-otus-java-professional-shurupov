package ru.otus.shurupov.myjunit.execution;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Counter {

    Map<CounterType, Integer> counters;

    public Counter() {
        counters = new HashMap<>();
        for (CounterType type : CounterType.values()) {
            counters.put(type, 0);
        }
    }

    public void add(CounterType type, int delta) {
        counters.put(type, counters.get(type) + delta);
    }

    public void inc(CounterType type) {
        add(type, 1);
    }

    public void report() {

        counters.put(CounterType.FAILED, counters.get(CounterType.TOTAL) - counters.get(CounterType.SUCCESS));

        System.out.println("Tests report");
        for (CounterType type : CounterType.values()) {
            System.out.println(type.name().toLowerCase() + ": " + counters.get(type));
        }
    }
}
