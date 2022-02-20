package ru.otus.java.solid.service;

import ru.otus.java.solid.domain.Banknote;
import ru.otus.java.solid.domain.Cash;
import ru.otus.java.solid.exception.NotEnoughBanknotesException;

public class CashMachine {

    private final Cash internalCash;

    public CashMachine() {
        internalCash = new Cash();
    }

    public CashMachine(Cash cash) {
        internalCash = new Cash(cash);
    }

    public Cash getMoney(int amount) {
        Cash cash = new Cash();

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
        for (var banknotes : cash.entrySet()) {
            for (int i = 0; i < banknotes.getValue(); i++) {
                internalCash.addBanknote(banknotes.getKey());
            }
        }
    }

    public int getBalance() {
        int balance = 0;
        for (var banknotes : internalCash.entrySet()) {
            balance += banknotes.getKey().getNominal() * banknotes.getValue();
        }
        return balance;
    }
}
