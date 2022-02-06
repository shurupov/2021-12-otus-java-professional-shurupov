package ru.calculator;

public class Data {
    private Integer value;

    public Data() {
    }

    public Data(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
