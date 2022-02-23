package ru.otus.java.solid.factory;

import ru.otus.java.solid.component.CashContainer;

public interface CashContainerFactory {
    CashContainer createEmptyCashContainer();
}
