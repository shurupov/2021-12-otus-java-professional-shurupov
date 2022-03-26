package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.exception.OrmException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private T map(ResultSet resultSet) {
        try {
            T object = entityClassMetaData.getConstructor().newInstance();
            entityClassMetaData.getAllFields().forEach(field -> {
                field.setAccessible(true);
                try {
                    field.set(object, resultSet.getObject(field.getName(), field.getType()));
                } catch (SQLException | IllegalAccessException throwable) {
                    throw new OrmException(throwable);
                }
            });
            return object;
        } catch (ReflectiveOperationException e) {
            throw new OrmException(e);
        }
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), this::map);
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelectList(connection, entitySQLMetaData.getSelectAllSql(), List.of(), this::map);
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
                    throw new OrmException(e);
                }
            })
            .collect(Collectors.toList());
        List<Object> values = new ArrayList<>(valuesWithoutId);
        try {
            values.add(entityClassMetaData.getIdField().get(object));
        } catch (IllegalAccessException e) {
            throw new OrmException(e);
        }
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), values);
    }
}
