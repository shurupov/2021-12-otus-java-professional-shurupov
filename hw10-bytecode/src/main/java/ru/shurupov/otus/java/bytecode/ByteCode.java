package ru.shurupov.otus.java.bytecode;

import ru.shurupov.otus.java.bytecode.aop.ProxyProcessor;
import ru.shurupov.otus.java.bytecode.process.TestLogging;
import ru.shurupov.otus.java.bytecode.process.impl.TestLoggingImpl;

public class ByteCode {
    public static void main(String[] args) {
        TestLogging proxy = (TestLogging) ProxyProcessor.getProxy(TestLogging.class, TestLoggingImpl.class);

        proxy.calculate(5);
        int d = proxy.calculate(5, 6);
        String d2 = proxy.calculate(5, 6, "7");
        proxy.anotherMethod("method");
    }
}
