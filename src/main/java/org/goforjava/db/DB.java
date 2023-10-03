package org.goforjava.db;

import org.goforjava.domain.Id;

import java.util.List;
import java.util.Optional;

public interface DB<T> {

    List<T> findAll();

    Optional<T> getById(Id id);

    void put(Id id, T toPut);
}
