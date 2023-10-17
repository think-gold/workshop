package org.goforjava.domain;

import org.goforjava.db.DB;
import org.goforjava.db.EmployeeDB;

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
                .sorted(Comparator.comparing((Employee e) -> e.getGrossSalary()).reversed())
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

            Long totalSalary = 0L;

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
        //znaleźć departament na podstawie lokalizacji
        //wypisać wszystkich pracownikow tego departamentu
        List<Employee> result = new ArrayList<>();

        List<Id> departmentsByLocation = departmentDB.findAll().stream()
                .filter(department -> department.getLocation().equals(location))
                .map(department -> department.getId())
                .toList();


        List<Employee> listOfEmployees = employeeDB.findAll();
//        listOfEmployees = employeeDB.findAll().stream()
//                .filter(employee -> employee.getDepartmentId().equals(id))
//                .toList();

        for (Id id : departmentsByLocation) {
            for (Employee employee : listOfEmployees) {
                if (employee.getDepartmentId().equals(id)) {
                    result.add(employee);
                }
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Long> countEmployeesByHireYear() {
        //  rok zatrudnienia / ile pracownikow
        Map<Integer, Long> result = new HashMap<>();
        List<Employee> listOfEmployees = employeeDB.findAll();


        List<Integer> years = new ArrayList<>();

        for (Employee employee : listOfEmployees) {
            int hireYear = employee.getHireDate().getYear();
            if (!years.contains(hireYear)) {
                years.add(hireYear);
                result.put(hireYear, 1L);
            } else {
                long updatedNumberOfEmployees = result.get(hireYear) + 1;
                result.replace(hireYear, updatedNumberOfEmployees);
            }
        }

        return result;
    }

    @Override
    public Map<Location, Long> countEmployeesByLocation() {
        //mapa: lokalizacja, rozmiar listy pracownikow by location
        //lista lokalizacji
        //lista pracownikow z lokalizacji
        Map<Location, Long> result = new HashMap<>();
        List<Location> listOfLocations = Arrays.stream(Location.values()).toList();

        for (Location location : listOfLocations) {
            long numberOfEmployeesByLocation = findEmployeesBasedIn(location).size();
            result.put(location, numberOfEmployeesByLocation);
        }

        return result;
    }

}
