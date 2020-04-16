package com.project.dao.GenericDAO;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao <I extends Serializable, E> {

    E findById(I id);

    List<E> findAll();

    void add(E entity);

    void update(E entity);

    void delete(E entity);

    void deleteById(I id);
}
