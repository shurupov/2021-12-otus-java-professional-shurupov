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

    private void processConfig(Collection<Class<?>> configClass) {
        for (Class<?> clazz : configClass) {
            checkConfigClass(clazz);
        }

        Queue<FactoryPair> factoryPairs = new LinkedList<>();

        for (Class<?> clazz : configClass) {
            Object configObject = createConfigurationObject(clazz);
            List<Method> methods = getFactoryMethods(clazz);
            for (Method method : methods) {
                factoryPairs.add(new FactoryPair(configObject, method));
            }
        }

        while (!factoryPairs.isEmpty()) {
            FactoryPair factoryPair = factoryPairs.poll();

            String componentName = factoryPair.method.getAnnotation(AppComponent.class).name();

            Class<?>[] parametersClasses = factoryPair.method.getParameterTypes();

            Object[] parameters = new Object[parametersClasses.length];

            boolean found = findAndFillParameters(parametersClasses, parameters);

            if (!found) {
                factoryPairs.add(factoryPair);
                continue;
            }

            Object component = createComponent(factoryPair.method, factoryPair.configObject, parameters);

            appComponents.add(component);
            appComponentsByName.put(componentName, component);
        }
    }

    private boolean findAndFillParameters(Class<?>[] parametersClasses, Object[] parameters) {
        for (int i = 0; i < parametersClasses.length; i++) {
            final int ii = i;
            Optional<Object> parameter = appComponents.stream()
                .filter(component -> isObjectClassOf(component, parametersClasses[ii]))
                .findAny();
            if (parameter.isPresent()) {
                parameters[i] = parameter.get();
            } else {
                return false;
            }
        }
        return true;
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
