package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ChangeFieldValuesProcessor implements Processor {
    @Override
    public Message process(Message message) {
        return message.toBuilder()
            .field12(message.getField11())
            .field11(message.getField12())
            .build();
    }
}
