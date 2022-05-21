# Concurrent Collections

## Task
Implement [SensorDataProcessorBuffered](src/main/java/ru/otus/services/processors/SensorDataProcessorBuffered.java) to be used between two threads. Internal buffer should be based on concurrent queue from `java.lang.concurrent` package. Do not use `synchronized` keyword.
[Main](src/main/java/ru/otus/Main.java) should work and tests should pass.