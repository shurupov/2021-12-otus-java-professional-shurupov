package ru.otus.java.solid.component;

import ru.otus.java.solid.domain.Banknote;
import ru.otus.java.solid.exception.NotEnoughBanknotesException;

import java.util.HashMap;
import java.util.Iterator;

public class CashContainerImpl extends HashMap<Banknote, Integer> implements CashContainer {

    public CashContainerImpl() {
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

    @Override
    public Iterator<Entry<Banknote, Integer>> iterator() {
        return entrySet().iterator();
    }
}
