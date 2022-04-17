package ru.otus.webserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.TemplateProcessor;
import ru.otus.crm.service.UserAuthService;
import ru.otus.servlet.ClientServlet;
import ru.otus.servlet.IndexServlet;
import ru.otus.servlet.LoginServlet;
import ru.otus.util.FileSystemHelper;
import ru.otus.webserver.filter.AuthFilter;

import java.io.IOException;
import java.net.URISyntaxException;

public class WebServerImpl implements WebServer {

    private static final String COMMON_RESOURCES_DIR = "static";

    private final Server server;
    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;
    private final UserAuthService authService;

    public WebServerImpl(int port, TemplateProcessor templateProcessor, DBServiceClient dbServiceClient, UserAuthService authService) {
        server = new Server(port);
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
        this.authService = authService;
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

    private void initContext() {

        ServletContextHandler servletContextHandler = createServletContextHandler();

        applySecurity(servletContextHandler, "/clients");

        HandlerList handlers = new HandlerList();
        handlers.addHandler(createResourceHandler());
        handlers.addHandler(servletContextHandler);

        server.setHandler(handlers);
    }

    private Handler createResourceHandler() {
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

    private void applySecurity(ServletContextHandler servletContextHandler, String path) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthFilter authFilter = new AuthFilter();
        servletContextHandler.addFilter(new FilterHolder(authFilter), path, null);
    }
}
