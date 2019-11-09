package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

import java.math.BigInteger;
import java.util.List;

public interface EmployeeDao extends Dao<Employee, BigInteger> {
    List<Employee> getByDepartment(Department department);
    List<Employee> getByManager(Employee employee);

}

