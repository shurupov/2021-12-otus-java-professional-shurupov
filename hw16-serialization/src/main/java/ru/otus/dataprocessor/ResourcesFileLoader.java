package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.jackson.MeasurementDeserializer;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final ObjectMapper mapper;

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;

        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public List<Measurement> load() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
            return mapper.readValue(inputStream, new TypeReference<List<Measurement>>() {});
        } catch (IOException e) {
            System.out.println("File reading failed");
            return List.of();
        }
    }
}
