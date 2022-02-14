package ru.shurupov.otus.java.bytecode.aop;

import ru.shurupov.otus.java.bytecode.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProxyProcessor {

    public static Object getProxy(Class<?> interfaze, Class<?> clazz) {
        try {
            return Proxy.newProxyInstance(
                ProxyProcessor.class.getClassLoader(),
                new Class<?>[]{ interfaze },
                new ProxyMethodHandler(clazz.getDeclaredConstructor().newInstance())
            );
        } catch (Exception e) {
            return null;
        }
    }

    static class ProxyMethodHandler implements InvocationHandler {

        private final Object innerObject;

        ProxyMethodHandler(Object innerObject) {
            this.innerObject = innerObject;
        }

        @Override
        public Object invoke(Object proxy, Method interfaceMethod, Object[] args) throws Throwable {

            Optional<Method> optionalMethod = Arrays.stream(innerObject.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .filter(m -> m.getName().equals(interfaceMethod.getName()))
                .filter(m -> m.getReturnType().equals(interfaceMethod.getReturnType()))
                .filter(m -> m.getParameterCount() == m.getParameterCount())
                .filter(m -> {
                    for (int i = 0; i < m.getParameterCount(); i++) {
                        if (!m.getParameterTypes()[i].equals(interfaceMethod.getParameterTypes()[i])) {
                            return false;
                        }
                    }
                    return true;
                })
                .findAny();

            if (optionalMethod.isPresent()) {
                Method method = optionalMethod.get();
                System.out.println("Executed method: " + method.getName() + ". Parameters: "
                    + Arrays.stream(args)
                    .map(a -> "(" + a.getClass().getName() + ") " + a)
                    .collect(Collectors.joining(", ", "[", "]"))
                );
            }

            return interfaceMethod.invoke(innerObject, args);
        }
    }
}
