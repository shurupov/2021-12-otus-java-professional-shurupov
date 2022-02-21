package ru.otus.java.solid.service;

import ru.otus.java.solid.component.CashContainer;
import ru.otus.java.solid.domain.Banknote;
import ru.otus.java.solid.exception.NotEnoughBanknotesException;
import ru.otus.java.solid.factory.CashContainerFactory;

public class CashMachine {

    private final CashContainerFactory cashContainerFactory;
    private final CashContainer internalCash;

    public CashMachine(CashContainerFactory cashContainerFactory, CashContainer cash) {
        this.cashContainerFactory = cashContainerFactory;
        this.internalCash = cash;
    }

    public CashContainer getMoney(int amount) {
        CashContainer cashContainer = cashContainerFactory.createEmptyCashContainer();

        for (Banknote banknote : Banknote.values()) {
            while (internalCash.containsBanknote(banknote) && amount >= banknote.getNominal()) {
                cashContainer.addBanknote(banknote);
                internalCash.removeBanknote(banknote);
                amount -= banknote.getNominal();
            }
        }

        if (amount > 0) {
            throw new NotEnoughBanknotesException();
        }

        return cashContainer;
    }

    public void addMoney(CashContainer cashContainer) {
        for (var banknotes : cashContainer) {
            for (int i = 0; i < banknotes.getValue(); i++) {
                internalCash.addBanknote(banknotes.getKey());
            }
        }
    }

    public int getBalance() {
        int balance = 0;
        for (var banknotes : internalCash) {
            balance += banknotes.getKey().getNominal() * banknotes.getValue();
        }
        return balance;
    }
}
