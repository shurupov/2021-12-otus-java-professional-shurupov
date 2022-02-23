package ru.otus.java.solid.factory;

import ru.otus.java.solid.component.CashContainer;
import ru.otus.java.solid.component.CashContainerImpl;

public class CashContainerFactoryImpl implements CashContainerFactory {
    @Override
    public CashContainer createEmptyCashContainer() {
        return new CashContainerImpl();
    }
}
