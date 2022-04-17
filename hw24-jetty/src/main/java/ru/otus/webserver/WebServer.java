package ru.otus.webserver;

public interface WebServer {
    void start() throws Exception;
    void join() throws InterruptedException;
    void stop() throws Exception;
}
