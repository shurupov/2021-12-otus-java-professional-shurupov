package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> aggregated = new LinkedHashMap<>();
        for (Measurement measurement : data) {
            String name = measurement.getName();
            double value = measurement.getValue();
            if (aggregated.containsKey(name)) {
                aggregated.put(name, aggregated.get(name) + value);
            } else {
                aggregated.put(name, value);
            }
        }
        return aggregated;
    }
}
