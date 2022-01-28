package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Credential;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CredentialRepository extends CrudRepository<Credential, Long> {

    @Query("select c from Credential c where c.email = :email")
    @EntityGraph(value = "credential-graph", type = EntityGraph.EntityGraphType.LOAD)
    Credential getCredentialByEmail(@Param("email") String email);

}
