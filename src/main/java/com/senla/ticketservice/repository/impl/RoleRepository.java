package com.senla.ticketservice.repository.impl;

import com.senla.ticketservice.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
public class RoleRepository extends AbstractRepository<Role> {

    public RoleRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        super(entityManager, criteriaBuilder, Role.class);
    }

    public Role findByName(String name) {
        return (Role) entityManager.createNativeQuery("select * from roles where role_name = '" + name + "'", Role.class)
                .getSingleResult();
        //return (Role) entityManager.createQuery("select r from Role r where r.role = '" + name + "'")
        //                .getSingleResult();
    }

}
