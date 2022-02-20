package ru.otus.java.solid.service;

import org.junit.jupiter.api.Test;
import ru.otus.java.solid.domain.Banknote;
import ru.otus.java.solid.domain.Cash;
import ru.otus.java.solid.exception.NotEnoughBanknotesException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class CashMachineTest {

    @Test
    void getMoney1() {
        CashMachine cashMachine = new CashMachine(initialCash());
        Cash cash = cashMachine.getMoney(1600);
        assertThat(cash)
            .containsEntry(Banknote.R1000, 1)
            .containsEntry(Banknote.R500, 1)
            .containsEntry(Banknote.R100, 1)
            .hasSize(3);
    }
    @Test
    void getMoney2() {
        CashMachine cashMachine = new CashMachine(initialCash());
        assertThatExceptionOfType(NotEnoughBanknotesException.class).isThrownBy(() -> {
            Cash cash = cashMachine.getMoney(15000);
        });
    }

    @Test
    void addMoney() {
        CashMachine cashMachine = new CashMachine(initialCash());
        cashMachine.addMoney(r200());
        assertThat(cashMachine.getBalance()).isEqualTo(8560);
    }

    @Test
    void getBalance() {
        CashMachine cashMachine = new CashMachine(initialCash());
        assertThat(cashMachine.getBalance()).isEqualTo(8360);
    }

    private Cash initialCash() {
        Cash cash = new Cash();
        cash.put(Banknote.R5000, 1);
        cash.put(Banknote.R1000, 2);
        cash.put(Banknote.R500, 2);
        cash.put(Banknote.R100, 3);
        cash.put(Banknote.R50, 1);
        cash.put(Banknote.R10, 1);
        return cash;
    }

    private Cash r200() {
        Cash cash = new Cash();
        cash.addBanknote(Banknote.R100);
        cash.addBanknote(Banknote.R100);
        return cash;
    }
}