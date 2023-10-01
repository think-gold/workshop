package org.goforjava;

import org.goforjava.db.DB;
import org.goforjava.db.DepartmentDB;
import org.goforjava.db.EmployeeDB;
import org.goforjava.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeStatsServiceTest {

  DB<Employee> employeeDB = new EmployeeDB();
  DB<Department> departmentDB = new DepartmentDB();

  EmployeeStatsService employeeStatsService =
      new WorkshopEmployeeStatsService(employeeDB, departmentDB);

  @BeforeAll
  void setupTests() {
    employeeDB.put(
        new Id("e1"),
        new Employee(
            new Id("e1"),
            "e1",
            LocalDate.of(1995, 1, 12),
            LocalDate.of(2020, 1, 15),
            1500L,
            "Senior Specialist",
            new Id("d1")));
  }

  @Test
  void shouldFindThreeTopCompensatedEmployees() {
    // when
    var threeTopCompensatedEmployees = employeeStatsService.findThreeTopCompensatedEmployees();

    // then
    assertEquals(1, threeTopCompensatedEmployees.size());
    assertEquals("e1", threeTopCompensatedEmployees.get(0).getId().getKey());
  }
}
