package ru.shurupov.homeowners.core.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class SimpleController {

    private static final Random random = new Random();

    @Value("${some.variable}")
    private String someValue;

    @Value("${some.secret.variable}")
    private String secretValue;

    @GetMapping("/api/try")
    public String getTry() {
        return "success " + random.nextInt();
    }

    @GetMapping("/api/variable")
    public String getVariable() {
        return someValue;
    }

    @GetMapping("/api/secret")
    public String getSecret() {
        return secretValue;
    }
}
