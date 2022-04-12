package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.TemplateProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ClientServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;

    public ClientServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Map<String, Object> data = Map.of(
            "clients", List.of(
                Client.builder()
                    .id(1L)
                    .name("name")
                    .address(Address.builder().street("Some street").build())
                    .phones(
                        List.of(
                            Phone.builder()
                                .number("12345")
                                .build(),
                            Phone.builder()
                                .number("12345")
                                .build()
                        )
                    )
                    .build()
            )
        );
        response.getWriter().write(templateProcessor.getPage("clients.ftl", data));
    }
}
