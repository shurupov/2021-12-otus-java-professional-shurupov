package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class EvenSecondExceptionProcessorTest {

    @Test
    void process1() {
        Processor processor = new EvenSecondExceptionProcessor(
            () -> LocalDateTime.of(5, Month.APRIL, 1, 1, 0, 2)
        );
        assertThrows(RuntimeException.class, () -> processor.process(null), "Even second");
    }

    @Test
    void process2() {
        Processor processor = new EvenSecondExceptionProcessor(
            () -> LocalDateTime.of(5, Month.APRIL, 1, 1, 0, 3)
        );

        assertDoesNotThrow(() -> processor.process(null));
    }
}