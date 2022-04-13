package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.TemplateProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;

    public ClientServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Map<String, Object> data = Map.of(
            "clients", dbServiceClient.findAll()
        );
        response.getWriter().write(templateProcessor.getPage("clients.ftl", data));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        var client = Client.builder()
            .name(request.getParameter("name"))
            .address(
                Address.builder()
                    .street(request.getParameter("address"))
                    .build()
            )
            .phones(
                Arrays.stream(request.getParameterMap().get("phones"))
                    .filter(StringUtils::isNotBlank)
                    .map(n -> Phone.builder().number(n).build())
                    .collect(Collectors.toList())
            )
            .build();

        dbServiceClient.saveClient(client);

        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "/clients");
    }
}
