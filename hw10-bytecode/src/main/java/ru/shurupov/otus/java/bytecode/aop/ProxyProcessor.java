package ru.shurupov.otus.java.bytecode.aop;

import ru.shurupov.otus.java.bytecode.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class ProxyProcessor {

    private static final Map<Class<?>, Object> proxies = new HashMap<>();

    public static Object getProxy(Class<?> interfaze, Class<?> clazz) {
        if (!proxies.containsKey(clazz)) {
            try {
                Object proxy = Proxy.newProxyInstance(
                    ProxyProcessor.class.getClassLoader(),
                    new Class<?>[]{interfaze},
                    new ProxyMethodHandler(interfaze, clazz)
                );
                proxies.put(clazz, proxy);
            } catch (Exception e) {
                return null;
            }
        }
        return proxies.get(clazz);
    }

    static class ProxyMethodHandler implements InvocationHandler {

        private final Object innerObject;
        private final List<Method> methodsToLog;

        ProxyMethodHandler(Class<?> interfaze, Class<?> clazz) throws ReflectiveOperationException {
            this.innerObject = clazz.getDeclaredConstructor().newInstance();

            List<Method> classMethodsToLog = Arrays.stream(clazz.getMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .collect(Collectors.toList());

            this.methodsToLog = Arrays.stream(interfaze.getMethods())
                .filter(
                    interfaceMethod -> classMethodsToLog.stream()
                        .filter(classMethod -> classMethod.getName().equals(interfaceMethod.getName()))
                        .filter(classMethod -> classMethod.getReturnType().equals(interfaceMethod.getReturnType()))
                        .filter(classMethod -> classMethod.getParameterCount() == interfaceMethod.getParameterCount())
                        .anyMatch(classMethod -> {
                            for (int i = 0; i < classMethod.getParameterCount(); i++) {
                                if (!classMethod.getParameterTypes()[i].equals(interfaceMethod.getParameterTypes()[i])) {
                                    return false;
                                }
                            }
                            return true;
                        })
                )

                .collect(Collectors.toList());

        }

        @Override
        public Object invoke(Object proxy, Method interfaceMethod, Object[] args) throws Throwable {
            if (methodsToLog.contains(interfaceMethod)) {
                System.out.println("Executed method: " + interfaceMethod.getName() + ". Parameters: "
                    + Arrays.stream(args)
                    .map(a -> "(" + a.getClass().getName() + ") " + a)
                    .collect(Collectors.joining(", ", "[", "]"))
                );
            }

            return interfaceMethod.invoke(innerObject, args);
        }
    }
}
