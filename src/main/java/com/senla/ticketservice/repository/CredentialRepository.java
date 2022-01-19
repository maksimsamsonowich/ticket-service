package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Credential;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface CredentialRepository extends CrudRepository<Credential, Long> {

    @EntityGraph(value = "credential-graph", type = EntityGraph.EntityGraphType.LOAD)
    Credential getCredentialByEmail(String email);

}
