package ru.otus.shurupov.springdatajdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "list";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      @RequestParam String address,
                      @RequestParam List<String> phones) {
        clientService.create(name, address, phones);
        return "redirect:/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {
        clientService.remove(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.get(id));
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @RequestParam String name,
                       @RequestParam String address,
                       @RequestParam List<String> phones) {
        clientService.update(id, name, address, phones);
        return "redirect:/";
    }
}
