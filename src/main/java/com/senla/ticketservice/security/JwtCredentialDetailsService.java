package com.senla.ticketservice.security;

import com.senla.ticketservice.entity.Credential;
import com.senla.ticketservice.repository.CredentialRepository;
import com.senla.ticketservice.security.jwt.user.JwtUserFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class JwtCredentialDetailsService implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential user = credentialRepository.getCredentialByEmail(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        return JwtUserFactory.jwtUserCreate(user);
    }

}
