package ru.otus.java.solid.service;

import org.junit.jupiter.api.Test;
import ru.otus.java.solid.component.CashContainerImpl;
import ru.otus.java.solid.domain.Banknote;
import ru.otus.java.solid.exception.NotEnoughBanknotesException;
import ru.otus.java.solid.factory.CashContainerFactoryImpl;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CashMachineTest {

    @Test
    void getMoney1() {
        CashMachine cashMachine = cashMachine();
        HashMap<Banknote, Integer> cash = (HashMap<Banknote, Integer>) cashMachine.getMoney(1600);
        assertThat(cash)
            .containsEntry(Banknote.R1000, 1)
            .containsEntry(Banknote.R500, 1)
            .containsEntry(Banknote.R100, 1)
            .hasSize(3);
    }
    @Test
    void getMoney2() {
        CashMachine cashMachine = cashMachine();
        assertThatExceptionOfType(NotEnoughBanknotesException.class).isThrownBy(() -> cashMachine.getMoney(15000));
    }

    @Test
    void addMoney() {
        CashMachine cashMachine = cashMachine();
        cashMachine.addMoney(r200());
        assertThat(cashMachine.getBalance()).isEqualTo(8560);
    }

    @Test
    void getBalance() {
        CashMachine cashMachine = cashMachine();
        assertThat(cashMachine.getBalance()).isEqualTo(8360);
    }

    private CashMachine cashMachine() {
        return new CashMachine(new CashContainerFactoryImpl(), initialCash());
    }

    private CashContainerImpl initialCash() {
        CashContainerImpl cash = new CashContainerImpl();
        cash.put(Banknote.R5000, 1);
        cash.put(Banknote.R1000, 2);
        cash.put(Banknote.R500, 2);
        cash.put(Banknote.R100, 3);
        cash.put(Banknote.R50, 1);
        cash.put(Banknote.R10, 1);
        return cash;
    }

    private CashContainerImpl r200() {
        CashContainerImpl cash = new CashContainerImpl();
        cash.addBanknote(Banknote.R100);
        cash.addBanknote(Banknote.R100);
        return cash;
    }
}