package org.example.javaeeweb.dao;

import java.util.List;

public interface IDao<T> {
    List<T> getAll();

    void add(T entity);

    void update(T entity);

    void delete(int id);
}

