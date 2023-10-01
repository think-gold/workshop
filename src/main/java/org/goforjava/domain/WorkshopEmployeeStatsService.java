package org.goforjava.domain;

import org.goforjava.db.DB;

import java.util.List;

public class WorkshopEmployeeStatsService implements EmployeeStatsService{

    private final DB<Employee> employeeDB;
    private final DB<Department> departmentDB;

    public WorkshopEmployeeStatsService(DB<Employee> employeeDB, DB<Department> departmentDB) {
        this.employeeDB = employeeDB;
        this.departmentDB = departmentDB;
    }

    @Override
    public List<Employee> findThreeTopCompensatedEmployees() {
        return null;
    }

    @Override
    public Department findDepartmentWithLowestCompensationAverage() {
        return null;
    }

    @Override
    public List<Employee> findEmployeesOlderThen(long years) {
        return null;
    }
}
