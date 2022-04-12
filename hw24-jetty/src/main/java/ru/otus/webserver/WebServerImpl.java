package ru.otus.webserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.JarFileResource;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.URLResource;
import ru.otus.crm.service.TemplateProcessor;
import ru.otus.servlet.ClientServlet;
import ru.otus.servlet.IndexServlet;
import ru.otus.util.FileSystemHelper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class WebServerImpl implements WebServer {

    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final Server server;
    private final TemplateProcessor templateProcessor;

    public WebServerImpl(int port, TemplateProcessor templateProcessor) {
        server = new Server(port);
        this.templateProcessor = templateProcessor;
    }

    @Override
    public void start() throws Exception {
        initContext();
        server.start();
    }

    @Override
    public void join() throws InterruptedException {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private void initContext() throws URISyntaxException, IOException {
        HandlerList handlers = new HandlerList();
//        handlers.addHandler(createResourceHandler());
        handlers.addHandler(createServletContextHandler());
//        handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*"));


        server.setHandler(handlers);
    }

    private Handler createResourceHandler() throws URISyntaxException, IOException {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));

        ContextHandler contextHandler= new ContextHandler("/static");
        contextHandler.setHandler(resourceHandler);

        return contextHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new IndexServlet()), "/");
        servletContextHandler.addServlet(new ServletHolder(new ClientServlet(templateProcessor)), "/clients/*");
        return servletContextHandler;
    }
}
