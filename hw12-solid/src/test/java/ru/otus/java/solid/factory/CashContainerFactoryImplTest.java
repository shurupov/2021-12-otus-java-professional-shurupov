package ru.otus.java.solid.factory;

import org.junit.jupiter.api.Test;
import ru.otus.java.solid.component.CashContainer;
import ru.otus.java.solid.domain.Banknote;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CashContainerFactoryImplTest {

    @Test
    void createEmptyCashContainer() {
        CashContainerFactoryImpl containerFactory = new CashContainerFactoryImpl();
        CashContainer cashContainer = containerFactory.createEmptyCashContainer();
        assertThat(((Map<Banknote, Integer>) cashContainer).isEmpty()).isTrue();
        assertThat(cashContainer.iterator().hasNext()).isFalse();
    }
}