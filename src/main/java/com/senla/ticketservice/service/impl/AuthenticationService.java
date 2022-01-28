package com.senla.ticketservice.service.impl;

import com.senla.ticketservice.dto.AuthenticationAnswerDto;
import com.senla.ticketservice.dto.AuthenticationRequestDto;
import com.senla.ticketservice.entity.Credential;
import com.senla.ticketservice.repository.CredentialRepository;
import com.senla.ticketservice.security.jwt.token.JwtTokenProvider;
import com.senla.ticketservice.service.IAuthenticationService;
import eu.senla.customlibrary.trackstatus.TrackStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final JwtTokenProvider tokenProvider;

    private final CredentialRepository credentialRepository;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationAnswerDto login(AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));

            Credential currentCredential = credentialRepository.getCredentialByEmail(email);

            if (currentCredential == null) {
                throw new UsernameNotFoundException("User with email: " + email + " not found");
            }

            String token = tokenProvider.createToken(email, currentCredential.getRoles().stream().toList());

            return new AuthenticationAnswerDto()
                    .setStatus(HttpStatus.OK.value())
                    .setEmail(email)
                    .setToken(token);

        } catch (AuthenticationException authenticationException) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
