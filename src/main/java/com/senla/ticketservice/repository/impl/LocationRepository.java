package com.senla.ticketservice.repository.impl;

import com.senla.ticketservice.entity.Event;
import com.senla.ticketservice.entity.Event_;
import com.senla.ticketservice.entity.Location;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class LocationRepository extends AbstractRepository<Location> {

    private final Connection connection;

    public LocationRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Connection connection) {
        super(entityManager, criteriaBuilder, Location.class);

        this.connection = connection;
    }

    @Override
    public Location readById(Long id) {
        return executeForResult(String.format("select * from locations where id = %d", id));
    }

    public Location getLocationByEvent(Long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("event-entity-graph");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);

        Root<Event> root = criteriaQuery.from(Event.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get(Event_.ID), id));

        TypedQuery<Event> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);

        return typedQuery.getSingleResult().getLocation();
    }

    private Location executeForResult(String sqlQuery) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            return Objects.requireNonNull(map(preparedStatement.executeQuery()));
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong ;(");
        }
    }

    private Location map(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return new Location(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("address"),
                resultSet.getInt("capacity"),
                null);
    }

}
