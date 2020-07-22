package com.project.dao;

import com.project.dao.abstraction.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<I extends Serializable, E> implements GenericDao<I, E> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<E> persistentClass;

    public AbstractDao(Class<E> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public E findById(I id) {
        return entityManager.find(persistentClass, id);
    }

    public List<E> findAll() {
        return entityManager.createQuery("FROM " + persistentClass.getName(), persistentClass).getResultList();
    }

    public void add(E entity) {
        entityManager.persist(entity);
    }

    public E update(E entity) {
        return entityManager.merge(entity);
    }

    public void delete(E entity) {
        entityManager.remove(entity);
    }

    public void deleteById(I id) {
        entityManager.createQuery("DELETE FROM " + persistentClass.getName() + " where id = :id").setParameter("id", id).executeUpdate();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
