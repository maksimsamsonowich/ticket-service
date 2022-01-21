package com.senla.ticketservice.repository.impl;

import com.senla.ticketservice.entity.*;
import com.senla.ticketservice.filter.EventFilterDto;
import com.senla.ticketservice.filter.PaginationDto;
import com.senla.ticketservice.filter.fields.enums.SortDirection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class EventRepository extends AbstractRepository<Event> {

    public EventRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        super(entityManager, criteriaBuilder, Event.class);
    }

    public Set<Event> getEventsByLocation(Long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("location-entity-graph");

        CriteriaQuery<Location> criteriaQuery = criteriaBuilder.createQuery(Location.class);

        Root<Location> root = criteriaQuery.from(Location.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get(Location_.ID), id));

        TypedQuery<Location> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);

        return typedQuery.getSingleResult().getEvents();
    }

    public List<Event> getAll(PaginationDto pagination) {
        CriteriaQuery<Event> cq = criteriaBuilder.createQuery(entityClass);
        Root<Event> rootEntry = cq.from(entityClass);
        CriteriaQuery<Event> all = cq.select(rootEntry);
        all.orderBy(createOrder((EventFilterDto) pagination, rootEntry));
        TypedQuery<Event> allQuery = entityManager.createQuery(all);
        allQuery.setMaxResults(pagination.getPageSize());
        allQuery.setFirstResult(pagination.getPageNumber());
        return allQuery.getResultList();
    }

    private List<Order> createOrder(EventFilterDto pagination, Root<Event> root) {
        List<Order> orders = new ArrayList<>();

        if (pagination.getByOccupiedPlace().getIsSorted()) {
            log.info("Sort by occupied places are enabled.");

            if (pagination.getByOccupiedPlace().getSortDirection() == SortDirection.ASCENDING) {
                orders.add(criteriaBuilder.asc(root.get(Event_.OCCUPIED_PLACE)));
            } else {
                orders.add(criteriaBuilder.desc(root.get(Event_.OCCUPIED_PLACE)));
            }
        }

        if (pagination.getByAgeLimit().getIsSorted()) {
            log.info("Sort by age limit are enabled.");

            if (pagination.getByAgeLimit().getSortDirection() == SortDirection.ASCENDING) {
                orders.add(criteriaBuilder.asc(root.get(Event_.AGE_LIMIT)));
            } else {
                orders.add(criteriaBuilder.desc(root.get(Event_.AGE_LIMIT)));
            }
        }

        if (pagination.getByDate().getIsSorted()) {
            log.info("Sort by date are enabled.");

            if (pagination.getByDate().getSortDirection() == SortDirection.ASCENDING) {
                orders.add(criteriaBuilder.asc(root.get(Event_.DATE)));
            } else {
                orders.add(criteriaBuilder.desc(root.get(Event_.DATE)));
            }
        }

        if (pagination.getByEventOrganizer().getIsSorted()) {
            log.info("Sort by event organizer are enabled.");

            if (pagination.getByEventOrganizer().getSortDirection() == SortDirection.ASCENDING) {
                orders.add(criteriaBuilder.asc(root.join(Event_.eventProgram).get(EventProgram_.PRICE)));
            } else {
                orders.add(criteriaBuilder.desc(root.join(Event_.eventProgram).get(EventProgram_.PRICE)));
            }
        }

        if (pagination.getByPrice().getIsSorted()) {
            log.info("Sort by price are enabled.");

            if (pagination.getByPrice().getSortDirection() == SortDirection.ASCENDING) {
                orders.add(criteriaBuilder.asc(root.get(Event_.EVENT_PROGRAM)));
            } else {
                orders.add(criteriaBuilder.desc(root.get(Event_.EVENT_PROGRAM)));
            }
        }
        if (orders.size() == 0) {
            log.info("There is no sort`s, enabled sort by id.");

            orders.add(criteriaBuilder.asc(root.get(Event_.ID)));
        }
        return orders;
    }

}
