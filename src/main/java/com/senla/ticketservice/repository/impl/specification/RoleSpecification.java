package com.senla.ticketservice.repository.impl.specification;

import com.senla.ticketservice.entity.Role;
import com.senla.ticketservice.entity.Role_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class RoleSpecification {

    public static Specification<Role> findByName(String name) {
        if (Objects.isNull(name))
            return null;

        return (root, query, cb) -> {
            return cb.like(root.get(Role_.ROLE), "%" + name + "%");
        };
    }

}
