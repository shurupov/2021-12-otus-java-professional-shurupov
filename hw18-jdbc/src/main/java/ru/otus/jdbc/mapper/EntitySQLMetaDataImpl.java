package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = :id", entityClassMetaData.getName(), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        return String.format(
            "insert into %s (%s) values (%s)",
            entityClassMetaData.getName(),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", ")),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> ':' + f.getName())
                .collect(Collectors.joining(", "))
        );
    }

    @Override
    public String getUpdateSql() {
        return String.format(
            "update %s set %s where %s",
            entityClassMetaData.getName(),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = :" + f.getName())
                .collect(Collectors.joining(", ")),
            entityClassMetaData.getIdField().getName() + " = :" + entityClassMetaData.getIdField().getName()
        );
    }
}
