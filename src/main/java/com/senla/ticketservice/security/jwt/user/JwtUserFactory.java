package com.senla.ticketservice.security.jwt.user;

import com.senla.ticketservice.entity.Credential;
import com.senla.ticketservice.entity.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
final public class JwtUserFactory {

    private final static String ROLE_PREFIX = "ROLE_";

    public static User jwtUserCreate(Credential credential) {
        return new User(
                credential.getEmail(),
                credential.getPassword(),
                mapToGrantedAuthority(credential.getRoles()));
    }

    public static List<GrantedAuthority> mapToGrantedAuthority(Set<Role> roleSet) {
        return roleSet.stream()
                .map(role ->
                        new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()))
                .collect(Collectors.toList());

    }

}
