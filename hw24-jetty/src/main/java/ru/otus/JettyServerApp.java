package ru.otus;

import ru.otus.webserver.WebServer;
import ru.otus.webserver.WebServerImpl;

public class JettyServerApp {
    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServerImpl(8080);
        webServer.start();
        webServer.join();
    }
}
