package ru.otus.shurupov.webflux.source.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class ExecutorConfig {

    public static final int THREAD_POOL_SIZE = 5;

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        var eventLoopGroup = new NioEventLoopGroup(THREAD_POOL_SIZE,
            new ThreadFactory() {
                private final AtomicLong threadIdGenerator = new AtomicLong(0);
                @Override
                public Thread newThread(@NonNull Runnable task) {
                    var thread = new Thread(task);
                    thread.setName("server-thread-" + threadIdGenerator.incrementAndGet());
                    return thread;
                }
            });

        var factory = new NettyReactiveWebServerFactory();
        factory.addServerCustomizers(builder -> builder.runOn(eventLoopGroup));

        return factory;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Bean
    public Scheduler timer() {
        return Schedulers.newParallel("scheduler-thread", 2);
    }
}
