package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.ConnectionSource;
import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DaoFactory {
    public EmployeeDao employeeDAO() {
        EmployeeDao employeeDao = new EmployeeDao() {
            @Override
            public List<Employee> getByDepartment(Department department)  {
                try {
                    ResultSet resultSet = getResultSet("select * from EMPLOYEE where DEPARTMENT = " + department.getId());
                    List<Employee> answer = getEmpList(resultSet);
                    resultSet.close();
                    return answer;
                }
                catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getByManager(Employee employee){
                try {
                    ResultSet resultSet = getResultSet("select * from EMPLOYEE where MANAGER = " + employee.getId());
                    List<Employee> answer = getEmpList(resultSet);
                    resultSet.close();
                    return answer;
                }catch (SQLException e){
                    return null;
                }
            }

            @Override
            public Optional<Employee> getById(BigInteger Id) {
                try {
                    ResultSet resultSet = getResultSet("select * from EMPLOYEE ");
                    Optional<Employee> answer = Optional.empty();
                    List<Employee> employees = getEmpList(resultSet);
                    resultSet.close();
                    for (Employee i : employees) {
                        if (i.getId().equals(Id)) {
                            answer = Optional.of(i);
                            break;
                        }
                    }
                    return answer;
                }
                catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getAll() {
                try {
                    ResultSet resultSet = getResultSet("select * from EMPLOYEE");
                    List<Employee> answer = getEmpList(resultSet);
                    resultSet.close();
                    return answer;
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public Employee save(Employee employee) {
                try {
                    ResultSet resultSet = getResultSet("select * from EMPLOYEE");
                    while (resultSet.next() && !String.valueOf(employee.getId()).equals(resultSet.getString("id"))) {
                    }
                    update(resultSet, employee);

                    if (resultSet.isAfterLast()) {
                        resultSet.insertRow();
                    } else resultSet.updateRow();

                    resultSet.close();
                    return employee;
                }
                catch (SQLException e){
                    return null;
                }
            }

            @Override
            public void delete(Employee employee) {
                try {
                    ResultSet resultSet = getResultSet("select * from EMPLOYEE where id = " +
                            employee.getId());
                    resultSet.next();
                    resultSet.deleteRow();
                    resultSet.close();
                } catch (SQLException e){
                    //nothing
                }
            }

            private List<Employee> getEmpList (ResultSet resultSet){
                List<Employee> employees = new LinkedList<Employee>();
                try{
                    while(resultSet.next()){
                        BigInteger id = new BigInteger(String.valueOf(resultSet.getInt("id")));
                        FullName fullName = new FullName(
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getString("middleName"));

                        Position position = Position.valueOf(resultSet.getString("position"));
                        LocalDate localDate = LocalDate.parse(resultSet.getString("hiredate"));
                        BigDecimal salary = new BigDecimal(String.valueOf(resultSet.getInt("salary")));
                        BigInteger manager = new BigInteger(String.valueOf(resultSet.getInt("manager")));
                        BigInteger department = new BigInteger(String.valueOf(resultSet.getInt("department")));
                        Employee employee = new Employee(id, fullName,position,localDate,salary,manager,department);
                        employees.add(employee);
                    }
                        return employees;
                }
                catch (SQLException e) {
                    return null;
                }
            }

            private ResultSet update (ResultSet resultSet, Employee employee) {
                try {
                    resultSet.updateInt("id", employee.getId().intValue());
                    resultSet.updateString("firstname", employee.getFullName().getFirstName());
                    resultSet.updateString("lastname", employee.getFullName().getLastName());
                    resultSet.updateString("middlename", employee.getFullName().getMiddleName());
                    resultSet.updateString("position", employee.getPosition().toString());
                    resultSet.updateInt("manager", employee.getManagerId().intValue());
                    resultSet.updateString("hiredate", employee.getHired().toString());
                    resultSet.updateDouble("salary", employee.getSalary().doubleValue());
                    resultSet.updateInt("department", employee.getDepartmentId().intValue());
                    return resultSet;
                }catch (SQLException e){
                    return null;
                }
            }
        };
        return employeeDao;
    }

    public DepartmentDao departmentDAO() {
        DepartmentDao departmentDao = new DepartmentDao() {
            @Override
            public Optional<Department> getById(BigInteger Id){
                try {
                    ResultSet resultSet = getResultSet("select * from DEPARTMENT");
                    List<Department> departments = getDepList(resultSet);
                    Optional<Department> answer = Optional.empty();
                    resultSet.close();
                    for (Department i : departments) {
                        if (i.getId().equals(Id)) {
                            answer = Optional.of(i);
                            break;
                        }
                    }
                    return answer;
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Department> getAll() {
                try {
                    ResultSet resultSet = getResultSet("select * from DEPARTMENT");
                    List<Department> answer = getDepList(resultSet);
                    resultSet.close();
                    return answer;
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public Department save(Department department) {
                try {
                    ResultSet resultSet = getResultSet("select * from DEPARTMENT");
                    while (resultSet.next() && !String.valueOf(department.getId()).equals(resultSet.getString("id"))) {
                    }
                    update(resultSet, department);

                    if (resultSet.isAfterLast()) {
                        resultSet.insertRow();
                    } else resultSet.updateRow();

                    resultSet.close();
                    return department;
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public void delete(Department department) {
                try {
                    ResultSet resultSet = getResultSet("select * from DEPARTMENT where id = " +
                            department.getId());
                    resultSet.next();
                    resultSet.deleteRow();
                    resultSet.close();
                } catch (SQLException e){
                    //nothing
                }
            }


            private List<Department> getDepList (ResultSet resultSet){
                List<Department> departments = new LinkedList<Department>();
                try{
                    while(resultSet.next()){
                        BigInteger id = BigInteger.valueOf(resultSet.getInt("id"));
                        String name = resultSet.getString("name");
                        String location = resultSet.getString("location");

                        Department department = new Department(id, name, location);
                        departments.add(department);
                    }
                    return departments;
                }
                catch (SQLException e) {
                    return null;
                }
            }
            private ResultSet update (ResultSet resultSet, Department department) {
                try {
                    resultSet.updateInt("id", department.getId().intValue());
                    resultSet.updateString("name", department.getName());
                    resultSet.updateString("location", department.getLocation());

                    return resultSet;
                } catch (SQLException e){
                    return null;
                }
            }
        };

        return departmentDao;
    }

    private ResultSet getResultSet(String str){
        try {
            return ConnectionSource.instance().createConnection().createStatement(1004, 1008).executeQuery(str);
        } catch (SQLException e){
            return null;
        }
    }
}
