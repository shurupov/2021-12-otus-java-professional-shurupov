package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class EvenSecondExceptionProcessor implements Processor {
    @Override
    public Message process(Message message) {
        if (LocalDateTime.now().get(ChronoField.SECOND_OF_MINUTE) % 2 == 0) {
            throw new RuntimeException("Even second");
        }
        return message;
    }
}
