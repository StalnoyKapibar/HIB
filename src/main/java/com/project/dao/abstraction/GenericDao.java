package com.project.dao.abstraction;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<I extends Serializable, E> {

    E findById(I id);

    List<E> findAll();

    void add(E entity);

    E update(E entity);

    void delete(E entity);

    void deleteById(I id);
}
