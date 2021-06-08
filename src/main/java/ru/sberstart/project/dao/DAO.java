package ru.sberstart.project.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    // create
    void save(T t);

    // read
    Optional<T> get(long id);

    List<T> getAll();

    // update
    void update(T t);

    // delete
    void delete(T t);
}
