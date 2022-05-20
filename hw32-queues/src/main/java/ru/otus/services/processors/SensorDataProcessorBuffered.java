package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final BlockingQueue<SensorData> queue;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.queue = new PriorityBlockingQueue<SensorData>(bufferSize, (o1, o2) -> {
            Duration duration = Duration.between(o2.getMeasurementTime(), o1.getMeasurementTime());
            return (int) duration.toMillis();
        });
    }

    @Override
    public void process(SensorData data) {
        queue.add(data);
        if (queue.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        try {
            List<SensorData> toFlush = new ArrayList<>(bufferSize);
            queue.drainTo(toFlush, bufferSize);
            if (!toFlush.isEmpty()) {
                writer.writeBufferedData(toFlush);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
