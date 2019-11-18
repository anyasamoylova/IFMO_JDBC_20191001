package com.efimchick.ifmo.web.jdbc.service;

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


public class DaoFactory {

            public static List<Employee> getEmpList(ResultSet resultSet) throws SQLException{
                List<Employee> employees = new LinkedList<Employee>();
                while(resultSet.next()){
                    Employee employee = getEmployee(resultSet, false);
                    employees.add(employee);
                }
                return employees;
            }

            public static Employee getEmployee(ResultSet resultSet, boolean needFull) throws SQLException {
                BigInteger id = new BigInteger(String.valueOf(resultSet.getInt("id")));
                FullName fullName = new FullName(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("middleName"));

                Position position = Position.valueOf(resultSet.getString("position"));
                LocalDate localDate = LocalDate.parse(resultSet.getString("hiredate"));
                BigDecimal salary = new BigDecimal(String.valueOf(resultSet.getInt("salary")));
                BigInteger departmentId = new BigInteger(String.valueOf(resultSet.getInt("department")));
                Employee manager = getManager(resultSet, needFull);
                Department department = getDepartmentById(departmentId);

                return new Employee(id, fullName,position,localDate,salary,manager,department);
            }

            public static Department getDepartmentById(BigInteger Id) throws SQLException {
                ResultSet resultSet = getResultSet("select * from DEPARTMENT");
                List<Department> departments = getDepList(resultSet);
                Department answer = null;
                resultSet.close();
                for (Department i : departments) {
                    if (i.getId().equals(Id)) {
                        answer = i;
                        break;
                    }
                }
                return answer;
            }

            private static List<Department> getDepList(ResultSet resultSet) throws SQLException {
                List<Department> departments = new LinkedList<Department>();
                while(resultSet.next()){
                    BigInteger id = BigInteger.valueOf(resultSet.getInt("id"));
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    Department department = new Department(id, name, location);
                    departments.add(department);
                    }
                return departments;
            }

            private static Employee getManager(ResultSet resultSet, boolean needFull) throws SQLException {
                ResultSet resultSet1 = getResultSet("select * from EMPLOYEE");
                Employee manager = null;
                int cur = resultSet.getRow();
                if (resultSet.getObject("manager") != null) {
                    int managerId = resultSet.getInt("manager");
                    resultSet1.absolute(0);
                    while(resultSet1.next()){
                        if(managerId == resultSet1.getInt("id"))
                            break;
                    }
                    if(!resultSet1.isAfterLast()){
                        if(!needFull){
                            manager = new Employee(
                                    new BigInteger(String.valueOf(resultSet1.getInt("id"))),
                                    new FullName(
                                            resultSet1.getString("firstName"),
                                            resultSet1.getString("lastName"),
                                            resultSet1.getString("middleName")),
                                    Position.valueOf(resultSet1.getString("position")),
                                    LocalDate.parse(resultSet1.getString("hireDate")),
                                    new BigDecimal(resultSet1.getInt("salary")),
                                    null,
                                    getDepartmentById(new BigInteger(String.valueOf(resultSet1.getInt("department"))))
                            );
                        } else {
                            resultSet.absolute(1);
                            while(resultSet1.getInt("id") != resultSet.getInt("id"))
                                resultSet.next();
                            manager = getEmployee(resultSet, true);
                        }
                    }
                }
                resultSet.absolute(cur);
                resultSet1.close();
                return manager;
            }

            public static ResultSet getResultSet(String str) throws SQLException {
                    return ConnectionSource.instance().createConnection().createStatement(1004, 1008).executeQuery(str);
            }
}
