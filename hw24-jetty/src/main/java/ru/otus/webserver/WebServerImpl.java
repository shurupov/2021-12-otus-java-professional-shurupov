package ru.otus.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.servlet.ClientServlet;
import ru.otus.servlet.IndexServlet;
import ru.otus.util.FileSystemHelper;

public class WebServerImpl implements WebServer {

    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final Server server;

    public WebServerImpl(int port) {
        server = new Server(port);
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
        HandlerList handlers = new HandlerList();
        handlers.addHandler(createServletContextHandler());
        handlers.addHandler(createResourceHandler());
//        handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*"));


        server.setHandler(handlers);
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new IndexServlet()), "/");
        servletContextHandler.addServlet(new ServletHolder(new ClientServlet()), "/clients/*");
        return servletContextHandler;
    }
}
