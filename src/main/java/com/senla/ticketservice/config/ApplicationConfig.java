package com.senla.ticketservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Configuration
public class ApplicationConfig {

    @Bean
    public CriteriaBuilder criteriaBuilder(EntityManager entityManager) {
        return entityManager.getCriteriaBuilder();
    }


}
