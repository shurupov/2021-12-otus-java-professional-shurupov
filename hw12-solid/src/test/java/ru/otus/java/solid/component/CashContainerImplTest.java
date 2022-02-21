package ru.otus.java.solid.component;

import org.junit.jupiter.api.Test;
import ru.otus.java.solid.exception.NotEnoughBanknotesException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.java.solid.domain.Banknote.*;

class CashContainerImplTest {

    @Test
    void containsBanknote() {
        CashContainerImpl cashContainer = new CashContainerImpl();
        cashContainer.addBanknote(R100);
        assertThat(cashContainer.containsBanknote(R100)).isTrue();
        assertThat(cashContainer.containsBanknote(R500)).isFalse();
    }

    @Test
    void addBanknote() {
        CashContainerImpl cashContainer = new CashContainerImpl();
        cashContainer.addBanknote(R100);
        assertThat(cashContainer.containsBanknote(R100)).isTrue();
        assertThat(cashContainer.containsBanknote(R500)).isFalse();
    }

    @Test
    void removeBanknote1() {
        CashContainerImpl cashContainer = new CashContainerImpl();
        cashContainer.addBanknote(R100);
        cashContainer.removeBanknote(R100);
        assertThat(cashContainer.containsBanknote(R100)).isFalse();
    }

    @Test
    void removeBanknote2() {
        CashContainerImpl cashContainer = new CashContainerImpl();
        cashContainer.addBanknote(R100);
        cashContainer.addBanknote(R100);
        cashContainer.removeBanknote(R100);
        assertThat(cashContainer.containsBanknote(R100)).isTrue();
        assertThat(cashContainer.get(R100)).isEqualTo(1);
    }

    @Test
    void removeBanknote3() {
        CashContainerImpl cashContainer = new CashContainerImpl();
        assertThatExceptionOfType(NotEnoughBanknotesException.class)
            .isThrownBy(() -> cashContainer.removeBanknote(R100));
    }

    @Test
    void iterator() {
        CashContainerImpl cashContainer = new CashContainerImpl();
        cashContainer.addBanknote(R100);
        cashContainer.addBanknote(R100);
        cashContainer.addBanknote(R500);
        cashContainer.addBanknote(R50);
        cashContainer.addBanknote(R5000);
        cashContainer.addBanknote(R5000);
        cashContainer.addBanknote(R5000);
        cashContainer.addBanknote(R1000);
        for (var banknotes : cashContainer) {
            assertThat(banknotes.getValue())
                .isEqualTo(cashContainer.get(banknotes.getKey()));
        }
    }
}