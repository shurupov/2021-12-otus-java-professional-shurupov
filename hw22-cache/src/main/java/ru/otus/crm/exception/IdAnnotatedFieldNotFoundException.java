package ru.otus.crm.exception;

public class IdAnnotatedFieldNotFoundException extends RuntimeException {
    public IdAnnotatedFieldNotFoundException(Class<?> clazz) {
        super("Not found field annotated with @Id annotation in class " + clazz.getCanonicalName());
    }
}
