package ru.otus.java.solid.domain;

public enum Banknote {

    R5000(5000),
    R1000(1000),
    R500(500),
    R100(100),
    R50(50),
    R10(10);

    private final int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
