package com.senla.ticketservice.repository.impl;

import com.senla.ticketservice.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
public class UserRepository extends AbstractRepository<User> {

    public UserRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        super(entityManager, criteriaBuilder, User.class);
    }

    public User findByUsername(String username) {
        return (User) entityManager.createQuery(
                        "select u from User u join fetch u.credential cr where cr.email = '" + username + "'")
                .getSingleResult();
    }

}