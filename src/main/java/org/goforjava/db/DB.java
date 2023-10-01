package org.goforjava.db;

import org.goforjava.domain.Id;

import java.util.Optional;

public interface DB <T> {
    Optional<T> getById(Id id);
    void put(Id id, T toPut);
}
