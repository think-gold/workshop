package org.goforjava.db;

import org.goforjava.domain.Department;
import org.goforjava.domain.Id;

import java.util.*;

public class DepartmentDB implements DB<Department> {

    private final Map<Id, Department> departmentMap = new HashMap<>();

    @Override
    public List<Department> findAll() {
        List<Department> listOfDepartments = new ArrayList<>();

        for(Department department : departmentMap.values()){
            listOfDepartments.add(department);
        }
        return listOfDepartments;
    }

    @Override
    public Optional<Department> findById(Id id) {
        Department searchedDepartment = null;
        for(Map.Entry<Id, Department> department : departmentMap.entrySet()){
            if(department.getKey().equals(id)){
                searchedDepartment = department.getValue();
                break;
            }
        }

        Optional<Department> departmentById = Optional.ofNullable(searchedDepartment);
        return departmentById;
    }

    @Override
    public void put(Id id, Department toPut) {
        departmentMap.put(id, toPut);
    }
}
