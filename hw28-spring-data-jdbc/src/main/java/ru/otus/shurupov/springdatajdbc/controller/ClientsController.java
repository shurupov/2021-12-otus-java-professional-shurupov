package ru.otus.shurupov.springdatajdbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller("/")
public class ClientsController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("clients", List.of());
        return "clients";
    }
}
