package org.goforjava.domain;

import java.util.List;

public interface EmployeeStatsService {

    // znajdź 3 najlepiej opłacanych pracowników, posortowanych malejąco
    List<Employee> findThreeTopCompensatedEmployees();

    Department findDepartmentWithLowestCompensationAverage();

    List<Employee> findEmployeesOlderThen(long years);
}
