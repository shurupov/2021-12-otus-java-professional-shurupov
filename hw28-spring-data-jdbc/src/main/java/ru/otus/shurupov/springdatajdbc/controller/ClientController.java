package ru.otus.shurupov.springdatajdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.shurupov.springdatajdbc.service.ClientService;

@Controller("/")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("clients", clientService.getAll());
        return "clients";
    }
}
