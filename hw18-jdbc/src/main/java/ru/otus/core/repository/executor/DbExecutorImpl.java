package ru.otus.core.repository.executor;

import ru.otus.core.sessionmanager.DataBaseOperationException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DbExecutorImpl implements DbExecutor {

    @Override
    public long executeStatement(Connection connection, String sql, List<Object> params) {
        try (var pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (var idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            try (var rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeInsert error", ex);
        }
    }

    @Override
    public <T> Optional<T> executeSelect(Connection connection, String sql, List<Object> params, Function<ResultSet, T> rsHandler) {
        try (var pst = connection.prepareStatement(sql)) {
            for (var idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            try (var rs = pst.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(rsHandler.apply(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeSelect error", ex);
        }
    }

    @Override
    public <T> List<T> executeSelectList(Connection connection, String sql, List<Object> params, Function<ResultSet, T> rsHandler) {
        try (var pst = connection.prepareStatement(sql)) {
            for (var idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            List<T> result = new ArrayList<>();
            try (var rs = pst.executeQuery()) {
                while (rs.next()) {
                    result.add(rsHandler.apply(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeSelect error", ex);
        }
    }
}
