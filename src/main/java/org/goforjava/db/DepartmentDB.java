package org.goforjava.db;

import org.goforjava.domain.Department;
import org.goforjava.domain.Id;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DepartmentDB implements DB<Department> {

    private final Map<Id, Department> departmentMap = new HashMap<>();

    @Override
    public Optional<Department> getById(Id id) {
        return Optional.ofNullable(departmentMap.get(id));
    }

    @Override
    public void put(Id id, Department toPut) {
        departmentMap.put(id, toPut);
    }
}
