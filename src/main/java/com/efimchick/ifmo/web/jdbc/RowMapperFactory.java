package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;


public class RowMapperFactory {

    public RowMapper<Employee> employeeRowMapper() {
        RowMapper<Employee> rowMapper = resultSet -> {
            try {
                BigInteger id = new BigInteger(String.valueOf(resultSet.getInt("id")));
                FullName fullName = new FullName(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("middleName"));

                Position position = Position.valueOf(resultSet.getString("position"));
                LocalDate localDate = LocalDate.parse(resultSet.getString("hiredate"));
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(resultSet.getInt("salary")));
                Employee employee = new Employee(id, fullName, position, localDate, bigDecimal);
                return employee;
            } catch (SQLException e) {
                return null;
            }
        };
        return rowMapper;
    }
}
