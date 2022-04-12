package ru.otus;

import ru.otus.crm.service.TemplateProcessor;
import ru.otus.crm.service.TemplateProcessorImpl;
import ru.otus.webserver.WebServer;
import ru.otus.webserver.WebServerImpl;

public class JettyServerApp {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/ftl/";

    public static void main(String[] args) throws Exception {

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        WebServer webServer = new WebServerImpl(WEB_SERVER_PORT, templateProcessor);
        webServer.start();
        webServer.join();
    }
}
