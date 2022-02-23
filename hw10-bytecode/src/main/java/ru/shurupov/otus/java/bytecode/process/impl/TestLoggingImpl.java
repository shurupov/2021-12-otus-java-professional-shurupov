package ru.shurupov.otus.java.bytecode.process.impl;

import ru.shurupov.otus.java.bytecode.annotation.Log;
import ru.shurupov.otus.java.bytecode.process.TestLogging;

public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculate(int param) {
        //something
    }

    @Log
    @Override
    public int calculate(int a, int b) {
        return 0;
    }

    @Override
    public String calculate(int a, int b, String c) {
        return a + b + c;
    }

    @Log
    @Override
    public void anotherMethod(String message) {
        System.out.println(message);
    }
}
