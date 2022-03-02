package ru.otus.processor.homework;

import ru.otus.factory.TimeFactory;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.temporal.ChronoField;

public class EvenSecondExceptionProcessor implements Processor {

    private final TimeFactory timeFactory;

    public EvenSecondExceptionProcessor(TimeFactory timeFactory) {
        this.timeFactory = timeFactory;
    }

    @Override
    public Message process(Message message) {
        if (timeFactory.getNowTime().get(ChronoField.SECOND_OF_MINUTE) % 2 == 0) {
            throw new RuntimeException("Even second");
        }
        return message;
    }
}
