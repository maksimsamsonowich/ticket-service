package com.senla.ticketservice.repository.impl;

import com.senla.ticketservice.repository.IAbstractRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

@Slf4j
@Repository
@AllArgsConstructor
public abstract class AbstractRepository<T> implements IAbstractRepository<T> {

    @PersistenceContext
    protected final EntityManager entityManager;

    protected final CriteriaBuilder criteriaBuilder;

    protected final Class<T> entityClass;

    @Override
    public T create(T entity) {
        entityManager.persist(entity);

        log.info("Repository: " + entityClass.getName() + " created.");

        return entity;
    }

    @Override
    public T readById(Long id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public T update(T entity) {
        entityManager.merge(entity);

        log.info("Repository: " + entityClass.getName() + " updated.");

        return entity;
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(readById(id));

        log.info("Repository: " + entityClass.getName() + " deleted.");
    }

}
