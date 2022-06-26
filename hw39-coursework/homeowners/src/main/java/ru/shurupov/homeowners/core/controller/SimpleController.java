package ru.shurupov.homeowners.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController("/api")
public class SimpleController {

    private static final Random random = new Random();

    @GetMapping("/try")
    public String getTry() {
        return "success " + random.nextInt();
    }
}
