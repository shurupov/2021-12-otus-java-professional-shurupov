package ru.otus.shurupov.webflux.source.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    public static final int THREAD_COUNT = 5;

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(THREAD_COUNT);
    }
}
