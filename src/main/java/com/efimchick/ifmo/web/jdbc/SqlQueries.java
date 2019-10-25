package com.efimchick.ifmo.web.jdbc;

/**
 * Implement sql queries like described
 */
public class SqlQueries {
    //Select all employees sorted by last name in ascending order
    //language=HSQLDB
    //String select01 = null;
    String select01 = "Select * from EMPLOYEE order by LASTNAME";

    //Select employees having no more than 5 characters in last name sorted by last name in ascending order
    //language=HSQLDB
    //String select02 = null;
    String select02 = "Select * from EMPLOYEE where length(LASTNAME) < 6 order by LASTNAME";

    //Select employees having salary no less than 2000 and no more than 3000
    //language=HSQLDB
    //String select03 = null;
    String select03 = "Select * from EMPLOYEE where SALARY >= 2000 and SALARY <= 3000";

    //Select employees having salary no more than 2000 or no less than 3000
    //language=HSQLDB
    //String select04 = null;
    String select04 = "Select * from EMPLOYEE where SALARY <= 2000 or SALARY >= 3000";

    //Select employees assigned to a department and corresponding department name
    //language=HSQLDB
    String select05 = "Select EMPLOYEE.*, DEPARTMENT.NAME from EMPLOYEE, DEPARTMENT where DEPARTMENT.ID = EMPLOYEE.DEPARTMENT";

    //Select all employees and corresponding department name if there is one.
    //Name column containing name of the department "depname".
    //language=HSQLDB
    //String select06 = null;
    String select06 = "Select EMPLOYEE.*, DEPARTMENT.NAME as DEPNAME from EMPLOYEE left outer join DEPARTMENT on DEPARTMENT.ID = EMPLOYEE.DEPARTMENT";

    //Select total salary pf all employees. Name it "total".
    //language=HSQLDB
    //String select07 = null;
    String select07 = "Select sum(EMPLOYEE.SALARY) as TOTAL from EMPLOYEE";

    //Select all departments and amount of employees assigned per department
    //Name column containing name of the department "depname".
    //Name column containing employee amount "staff_size".
    //language=HSQLDB
    //String select08 = null;
    String select08 = "Select count(EMPLOYEE.ID) as STAFF_SIZE, DEPARTMENT.NAME as DEPNAME from EMPLOYEE,DEPARTMENT where DEPARTMENT.ID = EMPLOYEE.DEPARTMENT group by DEPARTMENT.NAME";

    //Select all departments and values of total and average salary per department
    //Name column containing name of the department "depname".
    //language=HSQLDB
    //String select09 = null;\
    String select09 = "Select DEPARTMENT.NAME as DEPNAME, sum(SALARY) as total, avg(SALARY) as average from EMPLOYEE, DEPARTMENT where DEPARTMENT.ID = EMPLOYEE.DEPARTMENT group by DEPARTMENT.NAME ";

    //Select all employees and their managers if there is one.
    //Name column containing employee lastname "employee".
    //Name column containing manager lastname "manager".
    //language=HSQLDB
    //String select10 = null;
    String select10 = "Select  e.LASTNAME as employee, m.LASTNAME as manager from EMPLOYEE e left join EMPLOYEE m on E.MANAGER = M.ID order by MANAGER";

}
