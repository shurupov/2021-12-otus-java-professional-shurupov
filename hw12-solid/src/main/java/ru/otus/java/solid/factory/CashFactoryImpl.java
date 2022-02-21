package ru.otus.java.solid.factory;

import ru.otus.java.solid.domain.Cash;
import ru.otus.java.solid.domain.CashImpl;

public class CashFactoryImpl implements CashFactory {
    @Override
    public Cash createEmptyCash() {
        return new CashImpl();
    }
}
