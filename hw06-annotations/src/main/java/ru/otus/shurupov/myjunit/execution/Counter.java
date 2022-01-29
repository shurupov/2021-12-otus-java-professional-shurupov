package ru.otus.shurupov.myjunit.execution;

import java.util.HashMap;
import java.util.Map;

public class Counter {

    private final Map<CounterType, Integer> counters;

    public Counter() {
        counters = new HashMap<>();
        for (CounterType type : CounterType.values()) {
            counters.put(type, 0);
        }
    }

    public void add(CounterType type, int delta) {
        counters.compute(type, (k, v) -> (v == null) ? 0 : v + delta);;
    }

    public void inc(CounterType type) {
        add(type, 1);
    }

    public Map<CounterType, Integer> getReport() {
        counters.put(CounterType.FAILED, counters.get(CounterType.TOTAL) - counters.get(CounterType.SUCCESS));
        return Map.copyOf(counters);
    }
}
