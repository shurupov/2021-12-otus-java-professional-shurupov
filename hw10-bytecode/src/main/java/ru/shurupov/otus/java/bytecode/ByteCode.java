package ru.shurupov.otus.java.bytecode;

import ru.shurupov.otus.java.bytecode.aop.ProxyProcessor;
import ru.shurupov.otus.java.bytecode.classloader.CustomClassLoader;
import ru.shurupov.otus.java.bytecode.process.TestLogging;
import ru.shurupov.otus.java.bytecode.process.impl.TestLoggingImpl;

/*
* java -jar build/libs/bytecode-0.1.jar $(pwd)/build/classes/java/main/ru/shurupov/otus/java/bytecode/process/impl/TestLoggingImpl.class
* */

import java.io.IOException;

public class ByteCode {
    public static void main(String[] args) throws IOException {
        TestLogging proxy = (TestLogging) ProxyProcessor.getProxy(TestLogging.class, TestLoggingImpl.class);

        proxy.calculate(5);
        int d = proxy.calculate(5, 6);
        String d2 = proxy.calculate(5, 6, "7");
        proxy.anotherMethod("method");

        if (args.length > 0) {
            System.out.println("class in parameter is found");
            CustomClassLoader loader = new CustomClassLoader(args[0]);
            Class<?> clazz = loader.getClazz();
            TestLogging proxyOfLoaded = (TestLogging) ProxyProcessor.getProxy(TestLogging.class, clazz);
            proxyOfLoaded.calculate(5);
            int d3 = proxyOfLoaded.calculate(5, 6);
            String d4 = proxyOfLoaded.calculate(5, 6, "7");
            proxyOfLoaded.anotherMethod("method");
        }
    }
}
