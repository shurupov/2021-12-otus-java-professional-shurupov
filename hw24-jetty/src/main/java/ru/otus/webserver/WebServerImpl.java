package ru.otus.webserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.TemplateProcessor;
import ru.otus.servlet.ClientServlet;
import ru.otus.servlet.IndexServlet;
import ru.otus.util.FileSystemHelper;

import java.io.IOException;
import java.net.URISyntaxException;

public class WebServerImpl implements WebServer {

    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final Server server;
    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;

    public WebServerImpl(int port, TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        server = new Server(port);
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
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
        handlers.addHandler(createResourceHandler());
        handlers.addHandler(createServletContextHandler());
//        handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*"));


        server.setHandler(handlers);
    }

    private Handler createResourceHandler() throws URISyntaxException, IOException {
        ContextHandler contextHandler = new ContextHandler("/static"); /* the server uri path */
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        contextHandler.setHandler(resourceHandler);

        return contextHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new IndexServlet()), "/");
        servletContextHandler.addServlet(new ServletHolder(new ClientServlet(templateProcessor, dbServiceClient)), "/clients/*");
        return servletContextHandler;
    }
}
