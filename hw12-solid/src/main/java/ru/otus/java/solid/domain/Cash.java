package ru.otus.java.solid.domain;

import java.util.Map;

public interface Cash extends Iterable<Map.Entry<Banknote, Integer>> {

    boolean containsBanknote(Banknote banknote);

    void addBanknote(Banknote banknote);

    void removeBanknote(Banknote banknote);


}
