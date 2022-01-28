package ru.otus.shurupov.myjunit.execution;

import ru.otus.shurupov.myjunit.annotations.After;
import ru.otus.shurupov.myjunit.annotations.Before;
import ru.otus.shurupov.myjunit.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void start(Class<?> ...classes) {

        Counter counter = new Counter();

        for (Class<?> clazz : classes) {
            System.out.println("Class " + clazz.getName() + " execution started");
            performTestClass(clazz, counter);
            System.out.println("Class " + clazz.getName() + " execution ended");
            System.out.println();
        }

        counter.report();
    }

    public static void performTestClass(Class<?> clazz, Counter counter) {
        Method[] methods = clazz.getMethods();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        if (beforeMethods.size() > 1) {
            System.out.println("Class " + clazz.getName() + " has more than one before methods but it shouldn't");
            return;
        }

        if (afterMethods.size() > 1) {
            System.out.println("Class " + clazz.getName() + " has more than one after methods but it shouldn't");
            return;
        }

        var beforeMethod = beforeMethods.isEmpty() ? null : beforeMethods.get(0);
        var afterMethod = afterMethods.isEmpty() ? null : afterMethods.get(0);

        counter.add(CounterType.TOTAL, testMethods.size());

        try {
            for (Method method : testMethods) {
                test(clazz, method, beforeMethod, afterMethod, counter);
            }
        } catch (Throwable e) {
            processCreationException(clazz, e.getCause() != null ? e.getCause() : e);
        }
    }

    public static void test(Class<?> clazz, Method testMethod, Method beforeMethod, Method afterMethod, Counter counter) throws ReflectiveOperationException {
        Object testClassInstance = clazz.getDeclaredConstructor().newInstance();

        if (beforeMethod != null) {
            try {
                beforeMethod.invoke(testClassInstance);
            } catch (Throwable e) {
                processException("before method", clazz, beforeMethod, e.getCause());
                return;
            }
        }

        try {
            testMethod.invoke(testClassInstance);
            counter.inc(CounterType.SUCCESS);
        } catch (Throwable e) {
            processException("test method", clazz, testMethod, e.getCause());
        }

        if (afterMethod != null) {
            try {
                afterMethod.invoke(testClassInstance);
            } catch (Throwable e) {
                processException("after method", clazz, afterMethod, e.getCause());
            }
        }
    }

    private static void processCreationException(Class<?> clazz, Throwable cause) {
        String errorMessage = String.format(
            "Test class %s creation throws exception %s with message %s",
            clazz.getName(), cause != null ? cause.getClass().getName() : null, cause != null ? cause.getMessage() : null
        );
        System.out.println(errorMessage);
    }

    private static void processException(String operation, Class<?> clazz, Method method, Throwable cause) {
        String errorMessage = String.format(
            "Test class %s %s %s throws exception %s with message %s",
            clazz.getName(), operation, method.getName(), cause != null ? cause.getClass().getName() : null, cause != null ? cause.getMessage() : null
        );
        System.out.println(errorMessage);
    }
}
