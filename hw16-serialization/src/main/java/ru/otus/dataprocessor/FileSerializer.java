package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final File file;

    public FileSerializer(String fileName) {
        this.file = new File(fileName);
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            mapper.writeValue(file, data);
        } catch (JsonProcessingException e) {
            System.out.println("Serialization failed. " + e);
        } catch (IOException e) {
            System.out.println("File saving failed. " + e);
        }
    }
}
