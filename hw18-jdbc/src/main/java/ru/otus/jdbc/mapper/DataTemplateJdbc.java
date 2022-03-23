package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
            try {
                T object = entityClassMetaData.getConstructor().newInstance();
                entityClassMetaData.getAllFields().forEach(field -> {
                    field.setAccessible(true);
                    try {
                        field.set(object, resultSet.getObject(field.getName(), field.getType()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return object;
            } catch (ReflectiveOperationException e) {
                return null;
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T object) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList())
        );
    }

    @Override
    public void update(Connection connection, T object) {
        List<Object> valuesWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
            .map(f -> {
                try {
                    return f.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .collect(Collectors.toList());
        List<Object> values = new ArrayList<>(valuesWithoutId);
        try {
            values.add(entityClassMetaData.getIdField().get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), values);
    }
}
