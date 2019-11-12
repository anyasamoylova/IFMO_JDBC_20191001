package com.efimchick.ifmo.web.jdbc.service;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServiceFactory {

    public EmployeeService employeeService(){
        EmployeeService employeeService = new EmployeeService() {
            @Override
            public List<Employee> getAllSortByHireDate(Paging paging) {
                try{
                    String str ="select * from EMPLOYEE order by hiredate";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getAllSortByLastname(Paging paging) {
                try{
                    String str = "select * from EMPLOYEE order by lastname";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getAllSortBySalary(Paging paging) {
                try{
                    String str = "select * from EMPLOYEE order by salary";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getAllSortByDepartmentNameAndLastname(Paging paging) {
                try{
                    String str = "select * from EMPLOYEE order by department,lastname";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getByDepartmentSortByHireDate(Department department, Paging paging) {
                try{
                    String str = "select * from EMPLOYEE where department = " +
                            department.getId()+ " order by hiredate";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getByDepartmentSortBySalary(Department department, Paging paging) {
                try{
                    String str = "select * from EMPLOYEE where department = " +
                            department.getId()+ " order by salary";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getByDepartmentSortByLastname(Department department, Paging paging) {
                try{
                    String str = "select * from EMPLOYEE where department = " +
                            department.getId()+ " order by lastname";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getByManagerSortByLastname(Employee manager, Paging paging) {
                try{
                    String str = "select * from EMPLOYEE where manager = " +
                            manager.getId()+ " order by lastname";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getByManagerSortByHireDate(Employee manager, Paging paging) {
                try{
                    String str = "select * from EMPLOYEE where manager = " +
                            manager.getId()+ " order by hiredate";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public List<Employee> getByManagerSortBySalary(Employee manager, Paging paging) {
                try{
                    String str = "select * from EMPLOYEE where manager = " +
                            manager.getId()+ " order by salary";
                    return getAnswer(str, paging);
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public Employee getWithDepartmentAndFullManagerChain(Employee employee) {
                try{
                    ResultSet resultSet = DaoFactory.getResultSet("select * from EMPLOYEE");
                    resultSet.absolute(0);
                    while(resultSet.next()){
                        if(employee.getId().intValue() == resultSet.getInt("id"))
                            break;
                    }
                    Employee answer = DaoFactory.getEmployee(resultSet, true);
                    resultSet.close();
                    return answer;
                } catch (SQLException e){
                    return null;
                }
            }

            @Override
            public Employee getTopNthBySalaryByDepartment(int salaryRank, Department department) {
                try{
                    ResultSet resultSet = DaoFactory.getResultSet("select * from EMPLOYEE where department = " +
                            department.getId() + " order by salary desc");
                    List<Employee> answer = DaoFactory.getEmpList(resultSet);
                    resultSet.close();
                    return answer.get(salaryRank - 1);
                } catch (SQLException e){
                    return null;
                }
            }
        };
        return employeeService;
    }
    private List<Employee> getAnswer(String str, Paging paging) throws SQLException {
        ResultSet resultSet = DaoFactory.getResultSet(str);
        List<Employee> answer = DaoFactory.getEmpList(resultSet);
        resultSet.close();
        int min = paging.itemPerPage * (paging.page - 1);
        int max = paging.itemPerPage * paging.page;
        return answer.subList(min, (max > answer.size() ? answer.size(): max));
    }
}
