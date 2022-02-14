package ru.shurupov.otus.java.bytecode.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {

    private final Class<?> clazz;

    public CustomClassLoader(String filepath) throws IOException {
        byte[] bytecode = Files.readAllBytes(Paths.get(filepath));
        clazz = defineClass(null, bytecode, 0, bytecode.length);
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
