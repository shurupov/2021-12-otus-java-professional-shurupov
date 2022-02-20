package ru.otus.java.solid.domain;

import ru.otus.java.solid.exception.NotEnoughBanknotesException;

import java.util.HashMap;
import java.util.Map;

public class Cash extends HashMap<Banknote, Integer> implements Map<Banknote, Integer> {

    public Cash(Map<Banknote, Integer> cash) {
        this.putAll(cash);
    }
    public Cash() {
    }

    public boolean containsBanknote(Banknote banknote) {
        return containsKey(banknote) && get(banknote) > 0;
    }

    public void addBanknote(Banknote banknote) {
        int count = containsBanknote(banknote) ? get(banknote) : 0;
        count++;
        put(banknote, count);
    }

    public void removeBanknote(Banknote banknote) {
        if (!containsBanknote(banknote)) {
            throw new NotEnoughBanknotesException();
        }
        int count = get(banknote);
        count--;
        put(banknote, count);

    }

}
