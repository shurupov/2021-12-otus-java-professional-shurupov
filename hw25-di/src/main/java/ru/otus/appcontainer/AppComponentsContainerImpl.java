package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exception.ContainerInjectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> ...initialConfigClass) {
        processConfig(Arrays.stream(initialConfigClass).toList());
    }

    public AppComponentsContainerImpl(String packageName) {
        Set<Class<?>> classes = getClasses(packageName);
        processConfig(classes);
    }

    private Set<Class<?>> getClasses(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class);
    }

    private void processConfig(Collection<Class<?>> configClasses) {
        for (Class<?> clazz : configClasses) {
            checkConfigClass(clazz);
        }

        Queue<FactoryPair> factoryPairs = prepareDefinitions(configClasses);

        int notFound = 0;
        while (!factoryPairs.isEmpty()) {
            FactoryPair factoryPair = factoryPairs.poll();

            Class<?>[] parametersClasses = factoryPair.method.getParameterTypes();

            Object[] parameters;

            try {
                parameters = findAndFillParameters(parametersClasses);
            } catch (ContainerInjectionException e) {
                factoryPairs.add(factoryPair);
                notFound++;
                if (notFound > factoryPairs.size()) {
                    throw new ContainerInjectionException("Unable to satisfy all dependencies");
                }
                continue;
            }

            notFound = 0;

            addComponent(factoryPair, parameters);
        }
    }

    private Object[] findAndFillParameters(Class<?>[] parametersClasses) {
        Object[] parameters = new Object[parametersClasses.length];
        for (int i = 0; i < parametersClasses.length; i++) {
            final int ii = i;
            Object parameter = getAppComponent(parametersClasses[ii]);
            parameters[i] = parameter;
        }
        return parameters;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<C> found =  (List<C>) appComponents.stream()
            .filter(component -> componentClass.isAssignableFrom(component.getClass()))
            .collect(Collectors.toList());
        if (found.size() > 1) {
            throw new ContainerInjectionException("Found more than two component candidates");
        }
        if (found.isEmpty()) {
            throw new ContainerInjectionException("Component candidates not found");
        }
        return found.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C component = (C) appComponentsByName.get(componentName);
        if (component == null) {
            throw new ContainerInjectionException("Component candidates not found");
        }
        return component;
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

    private Queue<FactoryPair> prepareDefinitions(Collection<Class<?>> configClasses) {
        Queue<FactoryPair> factoryPairs = new LinkedList<>();

        for (Class<?> clazz : configClasses) {
            Object configObject = createConfigurationObject(clazz);
            List<Method> methods = getFactoryMethods(clazz);
            for (Method method : methods) {
                factoryPairs.add(new FactoryPair(configObject, method));
            }
        }
        return factoryPairs;
    }

    private void addComponent(FactoryPair factoryPair, Object[] parameters) {
        Object component = createComponent(factoryPair.method, factoryPair.configObject, parameters);
        String componentName = factoryPair.method.getAnnotation(AppComponent.class).name();

        appComponents.add(component);
        if (appComponentsByName.containsKey(componentName)) {
            throw new ContainerInjectionException(
                String.format("Container already contains component with name \"%s\"", componentName)
            );
        }
        appComponentsByName.put(componentName, component);
    }

    private List<Method> getFactoryMethods(Class<?> configClass) {
        List<Method> factoryMethods = new ArrayList<>();

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

    private static class FactoryPair {
        Object configObject;
        Method method;

        public FactoryPair(Object configObject, Method method) {
            this.configObject = configObject;
            this.method = method;
        }
    }
}
