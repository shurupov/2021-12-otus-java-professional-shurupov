package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName().toLowerCase(Locale.ROOT));
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?", entityClassMetaData.getName().toLowerCase(Locale.ROOT), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        return String.format(
            "insert into %s (%s) values (%s)",
            entityClassMetaData.getName().toLowerCase(Locale.ROOT),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", ")),
            String.join(", ", Collections.nCopies(entityClassMetaData.getFieldsWithoutId().size(), "?"))
        );
    }

    @Override
    public String getUpdateSql() {
        return String.format(
            "update %s set %s where %s",
            entityClassMetaData.getName().toLowerCase(Locale.ROOT),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", ")),
            entityClassMetaData.getIdField().getName() + " = ?"
        );
    }
}
