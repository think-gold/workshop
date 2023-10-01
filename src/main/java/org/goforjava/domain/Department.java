package org.goforjava.domain;

public class Department {
    private final Id id;
    private final String name;

    public Department(Id id, String name) {
        this.id = id;
        this.name = name;
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
