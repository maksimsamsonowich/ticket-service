package com.senla.ticketservice.repository.impl;

import com.senla.ticketservice.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
public class UserRepository extends AbstractRepository<User> {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder, JdbcTemplate jdbcTemplate) {
        super(entityManager, criteriaBuilder, User.class);

        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUsername(String username) {
        return jdbcTemplate
                .queryForObject("select * from users as u " +
                                "inner join creds as cr " +
                                "on u.creds_id = cr.id " +
                                "where cr.email = '" + username + "'",
                        User.class);
    }

}