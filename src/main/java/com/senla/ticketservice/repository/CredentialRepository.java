package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Credential;
import com.senla.ticketservice.repository.projection.CredentialProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    @Query("select c from Credential c where c.email = :email")
    @EntityGraph(value = "credential-graph", type = EntityGraph.EntityGraphType.LOAD)
    Credential getCredentialByEmail(@Param("email") String email);

    @Query("select c.id as id, " +
            "c.email as email, " +
            "c.password as password " +
            "from Credential c where id = :id")
    CredentialProjection getCredentialById(@Param("id") Long id);

}
