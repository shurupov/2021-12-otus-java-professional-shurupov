package ru.shurupov.otus.java.bytecode.process.impl;

import ru.shurupov.otus.java.bytecode.annotation.Log;
import ru.shurupov.otus.java.bytecode.process.TestLogging;

public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculate(int param) {
        System.out.println("TestLoggingImpl.calculate(" + param + ")");
    }

    @Log
    @Override
    public int calculate(int a, int b) {
        System.out.println("TestLoggingImpl.calculate(" + a + ", " + b + ")");
        return 0;
    }

    @Override
    public String calculate(int a, int b, String c) {
        System.out.println("TestLoggingImpl.calculate(" + a + ", " + b + ", " + c + ")");
        return a + b + c;
    }

    @Log
    @Override
    public void anotherMethod(String message) {
        System.out.println("TestLoggingImpl.anotherMethod(" + message + ")");
        System.out.println(message);
    }
}
