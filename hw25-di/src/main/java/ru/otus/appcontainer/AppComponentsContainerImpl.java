package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exception.ContainerInjectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        Object configObject = createConfigurationObject(configClass);

        Queue<Method> factoryMethods = getFactoryMethods(configClass);

        while (!factoryMethods.isEmpty()) {
            Method method = factoryMethods.poll();

            String componentName = method.getAnnotation(AppComponent.class).name();

            Class<?>[] parametersClasses = method.getParameterTypes();

            Object[] parameters = new Object[parametersClasses.length];

            boolean found = true;

            for (int i = 0; i < parametersClasses.length; i++) {
                final int ii = i;
                Optional<Object> parameter = appComponents.stream()
                    .filter(component -> isObjectClassOf(component, parametersClasses[ii]))
                    .findAny();
                if (parameter.isPresent()) {
                    parameters[i] = parameter.get();
                } else {
                    found = false;
                    break;
                }
            }

            if (!found) {
                factoryMethods.add(method);
                continue;
            }

            Object component = createComponent(method, configObject, parameters);

            appComponents.add(component);
            appComponentsByName.put(componentName, component);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
            .filter(component -> isObjectClassOf(component, componentClass))
            .findAny()
            .orElse(null);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private boolean isObjectClassOf(Object object, Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>(List.of(object.getClass().getInterfaces()));
        classes.addAll(List.of(object.getClass().getClasses()));
        classes.add(object.getClass());
        return classes.contains(clazz);
    }

    private Object createConfigurationObject(Class<?> configClass) {
        try {
            return configClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ContainerInjectionException(
                "Can't create an instance of configuration class",
                e
            );
        }
    }

    private Queue<Method> getFactoryMethods(Class<?> configClass) {
        Queue<Method> factoryMethods = new LinkedList<>();

        for (Method method : configClass.getMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                factoryMethods.add(method);
            }
        }

        return factoryMethods;
    }

    private Object createComponent(Method method, Object configObject, Object... parameters) {
        try {
            return method.invoke(configObject, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ContainerInjectionException(
                "Can't create an instance of a component",
                e
            );
        }
    }
}
