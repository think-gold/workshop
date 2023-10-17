package org.goforjava.db;

import org.goforjava.domain.Employee;
import org.goforjava.domain.Id;

import java.util.*;

public class EmployeeDB implements DB<Employee> {

    private final Map<Id, Employee> employeeMap = new HashMap<>();

    @Override
    public List<Employee> findAll() {
        List listOfEmployees = new ArrayList<>();
        for (Employee employee : employeeMap.values()) {
            listOfEmployees.add(employee);
        }
        return listOfEmployees;
    }

    @Override
    public Optional<Employee> findById(Id id) {
        Employee employeeById = null;
        for (Map.Entry<Id, Employee> employee : employeeMap.entrySet()) {
            if (employee.getKey().equals(id)) {
                employeeById = employee.getValue();
            }
        }
        return Optional.ofNullable(employeeById);
    }

    @Override
    public void put(Id id, Employee toPut) {
        employeeMap.put(id, toPut);
    }
}
