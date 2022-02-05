package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositorySpec extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}
