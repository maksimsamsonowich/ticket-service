package com.senla.ticketservice.repository.impl;

import com.senla.ticketservice.entity.Event;
import com.senla.ticketservice.entity.Event_;
import com.senla.ticketservice.entity.Ticket;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TicketRepository extends AbstractRepository<Ticket> {

    public TicketRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        super(entityManager, criteriaBuilder, Ticket.class);
    }

    public Set<Ticket> getEventTickets(Long id){
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> rootEvent = criteriaQuery.from(Event.class);
        rootEvent.fetch("tickets", JoinType.LEFT).fetch("owner", JoinType.LEFT);
        criteriaQuery.select(rootEvent);
        criteriaQuery.where(criteriaBuilder.equal(rootEvent.get(Event_.ID), id));

        TypedQuery<Event> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getSingleResult().getTickets();
    }

    public Set<Ticket> getTicketsByUser(Long id) {
        String queryString = "select t from Ticket t join fetch t.owner own join fetch t.eventHolding eh where t.id = :id";
        TypedQuery<Ticket> query = entityManager.createQuery(queryString, Ticket.class);
        query.setParameter("id", id);

        return new HashSet<>(query.getResultList());
    }

}
