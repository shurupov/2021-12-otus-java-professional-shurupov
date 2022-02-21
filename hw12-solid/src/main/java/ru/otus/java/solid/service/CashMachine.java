package ru.otus.java.solid.service;

import ru.otus.java.solid.domain.Banknote;
import ru.otus.java.solid.domain.Cash;
import ru.otus.java.solid.exception.NotEnoughBanknotesException;
import ru.otus.java.solid.factory.CashFactory;

public class CashMachine {

    private final CashFactory cashFactory;
    private final Cash internalCash;

    public CashMachine(CashFactory cashFactory, Cash cash) {
        this.cashFactory = cashFactory;
        this.internalCash = cash;
    }

    public Cash getMoney(int amount) {
        Cash cash = cashFactory.createEmptyCash();

        for (Banknote banknote : Banknote.values()) {
            while (internalCash.containsBanknote(banknote) && amount >= banknote.getNominal()) {
                cash.addBanknote(banknote);
                internalCash.removeBanknote(banknote);
                amount -= banknote.getNominal();
            }
        }

        if (amount > 0) {
            throw new NotEnoughBanknotesException();
        }

        return cash;
    }

    public void addMoney(Cash cash) {
        for (var banknotes : cash) {
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
