package ru.otus.factory;

import java.time.LocalDateTime;

public class TimeFactoryImpl implements TimeFactory {
    @Override
    public LocalDateTime getNowTime() {
        return LocalDateTime.now();
    }
}
