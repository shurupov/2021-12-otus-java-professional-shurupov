package ru.shurupov.otus.java.bytecode.process;

public interface TestLogging {
    void calculate(int param);
    int calculate(int a, int b);

    String calculate(int a, int b, String c);

    void anotherMethod(String message);
}
