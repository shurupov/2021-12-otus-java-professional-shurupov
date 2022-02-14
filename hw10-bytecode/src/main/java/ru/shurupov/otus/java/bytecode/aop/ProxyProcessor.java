package ru.shurupov.otus.java.bytecode.aop;

import ru.shurupov.otus.java.bytecode.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProxyProcessor {
    public static <I, C> Object getProxy(Class<I> interfaze, Class<C> clazz) {
        try {
            return Proxy.newProxyInstance(
                ProxyProcessor.class.getClassLoader(),
                new Class<?>[]{ interfaze },
                new ProxyMethodHandler<>(clazz.getDeclaredConstructor().newInstance())
            );
        } catch (Exception e) {
            return null;
        }
    }

    static class ProxyMethodHandler<C> implements InvocationHandler {

        private C innerObject;

        ProxyMethodHandler(C innerObject) {
            this.innerObject = innerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getAnnotation(Log.class) != null) {
                System.out.println("Executed method: " + method.getName() + ". Parameters: "
                    + Arrays.stream(method.getParameters())
                        .map(Object::toString)
                    .collect(Collectors.joining(", ", "[", "]"))
                );
            }
            return method.invoke(innerObject, args);
        }
    }
}
