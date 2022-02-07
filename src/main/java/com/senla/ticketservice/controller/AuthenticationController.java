package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.AuthenticationAnswerDto;
import com.senla.ticketservice.dto.AuthenticationRequestDto;
import com.senla.ticketservice.dto.CredentialDto;
import com.senla.ticketservice.service.IAuthenticationService;
import com.senla.ticketservice.service.ICredentialService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@NoArgsConstructor
public class AuthenticationController {

    @Autowired
    private ICredentialService iCredentialService;

    @Autowired
    private IAuthenticationService iAuthenticationService;

    @PostMapping("auth")
    @PreAuthorize("permitAll")
    public ResponseEntity<AuthenticationAnswerDto> customerAuthentication
            (@RequestBody AuthenticationRequestDto requestDto) {
        log.info("Authentication controller received the post request (customerAuthentication).");

        AuthenticationAnswerDto authenticationAnswer =
                iAuthenticationService.login(requestDto);

        return ResponseEntity.ok(authenticationAnswer);
    }

    @PreAuthorize("permitAll")
    @PostMapping("register")
    public ResponseEntity<AuthenticationAnswerDto> customerRegistration
            (@RequestBody CredentialDto credentialDto) {
        log.info("Authentication controller received the post request (customerRegistration).");

        iCredentialService.createCredential(credentialDto);

        AuthenticationRequestDto authenticationData =
                new AuthenticationRequestDto()
                        .setEmail(credentialDto.getEmail())
                        .setPassword(credentialDto.getPassword());

        AuthenticationAnswerDto authenticationAnswer =
                iAuthenticationService.login(authenticationData);

        return ResponseEntity.ok(authenticationAnswer);
    }

}
