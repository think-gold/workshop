package org.goforjava.db;

import org.goforjava.domain.Department;
import org.goforjava.domain.Id;

import java.util.*;

public class DepartmentDB implements DB<Department> {

    private final Map<Id, Department> departmentMap = new HashMap<>();

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departmentMap.values());
    }

    @Override
    public Optional<Department> getById(Id id) {
        return Optional.ofNullable(departmentMap.get(id));
    }

    @Override
    public void put(Id id, Department toPut) {
        departmentMap.put(id, toPut);
    }
}
