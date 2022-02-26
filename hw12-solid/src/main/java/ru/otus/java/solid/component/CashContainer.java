package ru.otus.java.solid.component;

import ru.otus.java.solid.domain.Banknote;

import java.util.Map;

public interface CashContainer extends Iterable<Map.Entry<Banknote, Integer>> {

    boolean containsBanknote(Banknote banknote);

    void addBanknote(Banknote banknote);

    void removeBanknote(Banknote banknote);


}
