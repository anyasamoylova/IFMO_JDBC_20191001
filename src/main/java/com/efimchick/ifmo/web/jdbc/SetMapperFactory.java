package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
        SetMapper<Set<Employee>> setMapper = resultSet -> {
            Set<Employee> employeeSet = new HashSet<>();
            try{
                while(resultSet.next() == true){
                    Employee employee = mapRow(resultSet);
                    employeeSet.add(employee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return employeeSet;
        };
        return setMapper;
    }

    private Employee mapRow(ResultSet resultSet) {
        try {
            BigInteger id = new BigInteger(String.valueOf(resultSet.getInt("id")));
            FullName fullName = new FullName(
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("middleName"));

            Position position = Position.valueOf(resultSet.getString("position"));
            LocalDate localDate = LocalDate.parse(resultSet.getString("hiredate"));
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(resultSet.getInt("salary")));
            Employee manager = getManager(resultSet);
            return new Employee(id, fullName, position, localDate, bigDecimal, manager);
        } catch (SQLException e) {
            return null;
        }
    }

    private Employee getManager(ResultSet resultSet) throws SQLException {
        Employee manager = null;
        if (resultSet.getObject("manager") == null)
            return null;
        else {
            int cur = resultSet.getRow();
            int idManager = resultSet.getInt("manager");
            resultSet.absolute(0);
            while (resultSet.next() == true) {
                if (idManager == resultSet.getInt("id")) {
                    manager = mapRow(resultSet);
                    break;
                }
            }
            resultSet.absolute(cur);
        }
        return manager;
    }
}



