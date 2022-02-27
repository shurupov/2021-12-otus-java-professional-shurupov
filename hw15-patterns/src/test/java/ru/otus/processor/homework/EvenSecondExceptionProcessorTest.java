package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import static org.junit.jupiter.api.Assertions.*;

class EvenSecondExceptionProcessorTest {

    @Test
    void process() throws InterruptedException {
        Processor processor = new EvenSecondExceptionProcessor();
        while (LocalDateTime.now().get(ChronoField.SECOND_OF_MINUTE) % 2 > 0) {
            Thread.sleep(400);
        }
        assertThrows(RuntimeException.class, () -> processor.process(null), "Even second");

        while (LocalDateTime.now().get(ChronoField.SECOND_OF_MINUTE) % 2 == 0) {
            Thread.sleep(400);
        }

        assertDoesNotThrow(() -> processor.process(null));
    }
}