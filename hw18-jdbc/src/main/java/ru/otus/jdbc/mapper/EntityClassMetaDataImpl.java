package ru.otus.jdbc.mapper;

import ru.otus.crm.annotation.Id;
import ru.otus.crm.exception.IdAnnotatedFieldNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    private final String name;
    private final List<Field> fields;
    private final Field idField;
    private final List<Field> fieldsWithoutId;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> clazz) throws NoSuchMethodException {
        this.clazz = clazz;
        this.name = clazz.getSimpleName();
        this.fields = List.of(clazz.getFields());
        this.idField = fields.stream()
            .filter(f -> f.isAnnotationPresent(Id.class))
            .findAny()
            .orElseThrow(() -> new IdAnnotatedFieldNotFoundException(this.clazz));
        this.fieldsWithoutId = getAllFields().stream()
            .filter(f -> !f.isAnnotationPresent(Id.class))
            .collect(Collectors.toList());
        this.constructor = clazz.getConstructor();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
