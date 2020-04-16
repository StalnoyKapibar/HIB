package com.project.dao.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDAO<I extends Serializable, E> implements IGenericDao<I, E> {

    @PersistenceContext
    EntityManager entityManager;

    private final Class<E> persistentClass;

    public AbstractDAO(Class<E> persistentClass){
        this.persistentClass = persistentClass;
    }

    public E findById(I id) {
        return entityManager.find(persistentClass, id);
    }

    @SuppressWarnings("ALL")
    public List<E> findAll() {
        return entityManager.createQuery("from " + persistentClass.getName()).getResultList();
    }

    public void add(E entity) {
        entityManager.persist(entity);
    }

    public void update(E entity) {
        entityManager.merge(entity);
    }

    public void delete(E entity) {
        entityManager.remove(entity);
    }

    public void deleteById(I id) {
        E entity = findById(id);
        delete(entity);
    }
}
