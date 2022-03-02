package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ChangeFieldValuesProcessorTest {

    @Test
    void process() {
        Processor processor = new ChangeFieldValuesProcessor();
        Message message1 = new Message.Builder(1)
            .field11("11")
            .field12("12")
            .build();
        Message message2 = processor.process(message1);

        assertThat(message2.getField11()).isEqualTo(message1.getField12());
        assertThat(message2.getField12()).isEqualTo(message1.getField11());
    }
}