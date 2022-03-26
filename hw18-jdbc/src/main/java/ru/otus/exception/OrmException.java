package ru.otus.exception;

public class OrmException extends RuntimeException {
    public OrmException(Exception cause) {
        super(cause);
    }
}
