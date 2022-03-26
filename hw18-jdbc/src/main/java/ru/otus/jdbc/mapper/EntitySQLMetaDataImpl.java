package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.selectAllSql = String.format("select * from %s", entityClassMetaData.getName().toLowerCase(Locale.ROOT));
        this.selectByIdSql = String.format(
            "select * from %s where %s = ?",
            entityClassMetaData.getName().toLowerCase(Locale.ROOT),
            entityClassMetaData.getIdField().getName()
        );
        this.insertSql = String.format(
            "insert into %s (%s) values (%s)",
            entityClassMetaData.getName().toLowerCase(Locale.ROOT),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", ")),
            String.join(", ", Collections.nCopies(entityClassMetaData.getFieldsWithoutId().size(), "?"))
        );
        this.updateSql = String.format(
            "update %s set %s where %s",
            entityClassMetaData.getName().toLowerCase(Locale.ROOT),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", ")),
            entityClassMetaData.getIdField().getName() + " = ?"
        );
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }
}
