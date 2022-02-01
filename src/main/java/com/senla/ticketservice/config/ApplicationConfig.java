package com.senla.ticketservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ApplicationConfig {

    @Bean
    public CriteriaBuilder criteriaBuilder(EntityManager entityManager) {
        return entityManager.getCriteriaBuilder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
