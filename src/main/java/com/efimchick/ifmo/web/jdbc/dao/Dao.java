package com.efimchick.ifmo.web.jdbc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T, Id> {

    Optional<T> getById(Id Id) throws SQLException;

    List<T> getAll() throws SQLException;

    T save(T t) throws SQLException;

    void delete(T t) throws SQLException;
}

