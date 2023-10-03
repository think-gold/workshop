package org.goforjava;

import org.goforjava.db.DB;
import org.goforjava.db.DepartmentDB;
import org.goforjava.db.EmployeeDB;
import org.goforjava.domain.Department;
import org.goforjava.domain.Employee;
import org.goforjava.domain.EmployeeStatsService;
import org.goforjava.domain.WorkshopEmployeeStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeStatsServiceTest {

    DB<Employee> employeeDB = new EmployeeDB();
    DB<Department> departmentDB = new DepartmentDB();

    MockDataFactory mockDataFactory = new MockDataFactory(employeeDB, departmentDB);
    EmployeeStatsService employeeStatsService =
            new WorkshopEmployeeStatsService(employeeDB, departmentDB);

    @BeforeEach
    void setupTests() {
        mockDataFactory.populateDepartments();
        mockDataFactory.populateEmployees();
    }

    @Test
    void shouldFindThreeTopCompensatedEmployees() {
        // when
        var threeTopCompensatedEmployees = employeeStatsService.findThreeTopCompensatedEmployees();

        // then
        assertEquals(3, threeTopCompensatedEmployees.size());
        assertEquals("e7", threeTopCompensatedEmployees.get(0).getId().getKey());
        assertEquals("e6", threeTopCompensatedEmployees.get(1).getId().getKey());
        assertEquals("e12", threeTopCompensatedEmployees.get(2).getId().getKey());
    }

    @Test
    void shouldFindDepartmentWithLowestCompensationAverage() {
        Department departmentWithLowestCompensationAverage = employeeStatsService.findDepartmentWithLowestCompensationAverage();

        assertEquals("d3", departmentWithLowestCompensationAverage.getId().getKey());
        assertEquals("Departament Kontroli Nad Magicznymi Stworzeniami", departmentWithLowestCompensationAverage.getName());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "100, 1",
            "80, 5",
            "70, 8",
            "55, 16",
            "43, 28",
    })
    void shouldFindEmployeesOlderThan(int minAge, int expectedCount) {
        List<Employee> employeesOlderThen = employeeStatsService.findEmployeesOlderThen(minAge);

        assertEquals(expectedCount, employeesOlderThen.size());
    }
}
