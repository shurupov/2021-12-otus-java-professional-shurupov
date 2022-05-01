package ru.otus.shurupov.springdatajdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.shurupov.springdatajdbc.service.ClientService;

import java.util.List;

@Controller("/")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("clients", clientService.getAll());
        return "clients";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      @RequestParam String address,
                      @RequestParam List<String> phones) {
        clientService.create(name, address, phones);
        return "redirect:/";
    }
}
