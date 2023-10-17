package org.goforjava.domain;

import org.goforjava.db.DB;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class WorkshopEmployeeStatsService implements EmployeeStatsService {

    private final DB<Employee> employeeDB;
    private final DB<Department> departmentDB;

    public WorkshopEmployeeStatsService(DB<Employee> employeeDB, DB<Department> departmentDB) {
        this.employeeDB = employeeDB;
        this.departmentDB = departmentDB;
    }

    public static void main(String[] args) {

    }

    @Override
    public List<Employee> findEmployeesOlderThen(long years) {

        List<Employee> searchedEmployee = employeeDB.findAll().stream().filter((employee -> LocalDate.now().getYear() - employee.getBirthDate().getYear() >= years)).toList();
        return searchedEmployee;
    }

    @Override
    public List<Employee> findThreeTopCompensatedEmployees() {

        List<Employee> searchedEmployees = employeeDB.findAll().stream()
                .sorted(Comparator.comparing((Employee e) -> e.getGrossSalary()))
                .limit(3)
                .toList();
        return searchedEmployees;
    }

    @Override
    public Optional<Department> findDepartmentWithLowestCompensationAverage() {
        //szukamy jednego departamentu
        //dla kazdego departamentu trzeba policzyc srednia zarobkow pracownikow
        //wyszuać departament z min średnią
        Map<Id, Float> averageSalaryByDep = new HashMap<>();            //Mapa zawierająca Id departamentu i średnią zarobków pracowników
        List<Department> listOfDepartments = departmentDB.findAll();
        List<Employee> listOfEmployees = employeeDB.findAll();

        for (Department department : listOfDepartments) {

            Id depId = department.getId();

            List<Long> listOfSalaryByDep = listOfEmployees.stream()
                    .filter(employee -> employee.getDepartmentId().equals(depId))
                    .map(employee -> employee.getGrossSalary())
                    .toList();

            //wylicznie sredniej zarobków z danego departamentu:
            if (listOfSalaryByDep.size() == 0) {
                continue;
            }

            Long totalSalary = 0l;

            for (long salary : listOfSalaryByDep) {
                totalSalary += salary;
            }

            float averageSalary = totalSalary / listOfSalaryByDep.size();
            averageSalaryByDep.put(depId, averageSalary);
        }

        float minSalary = Float.MAX_VALUE;
        Id searchedDepId = null;

        //szukam min wynagrodzenia departamentu i zwracam jego Id
        for (Map.Entry<Id, Float> entry : averageSalaryByDep.entrySet()) {
            if (entry.getValue() < minSalary) {
                minSalary = entry.getValue();
                searchedDepId = entry.getKey();
            }
        }

        Optional<Department> searchedDep = departmentDB.findById(searchedDepId);

        return searchedDep;
    }


    @Override
    public List<Employee> findEmployeesBasedIn(Location location) {
        return List.of();
    }

    @Override
    public Map<Integer, Long> countEmployeesByHireYear() {
        return Map.of();
    }

    @Override
    public Map<Location, Long> countEmployeesByLocation() {
        return Map.of();
    }

    @Override
    public List<Employee> getListOfEmployeeForDep(Id depId) {
        List<Employee> salaryByDep = employeeDB.findAll().stream()
                .filter(employee -> employee.getDepartmentId() == depId)
                //.map(employee -> employee.getGrossSalary())
                .toList();
        return salaryByDep;
    }
}
