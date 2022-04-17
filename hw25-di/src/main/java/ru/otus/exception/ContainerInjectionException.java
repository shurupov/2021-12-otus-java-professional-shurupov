package ru.otus.exception;

public class ContainerInjectionException extends RuntimeException {

    public ContainerInjectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContainerInjectionException(String message) {
        super(message);
    }
}
