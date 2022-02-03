package com.senla.ticketservice.repository;


public interface IAbstractRepository<T> {

    T create(T entity);

    T readById(Long id);

    T update(T entity);

    void deleteById(Long id);

}