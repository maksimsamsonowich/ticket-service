package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.AuthenticationAnswerDto;
import com.senla.ticketservice.dto.AuthenticationRequestDto;
import com.senla.ticketservice.dto.CredentialDto;
import com.senla.ticketservice.service.IAuthenticationService;
import com.senla.ticketservice.service.ICredentialService;
import eu.senla.customlibrary.trackstatus.TrackStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final ICredentialService iCredentialService;

    private final IAuthenticationService iAuthenticationService;

    @TrackStatus
    @PostMapping("auth")
    @PreAuthorize("permitAll")
    public ResponseEntity<AuthenticationAnswerDto> customerAuthentication
            (@RequestBody AuthenticationRequestDto requestDto) {
        log.info("Authentication controller received the post request (customerAuthentication).");

        AuthenticationAnswerDto authenticationAnswer =
                iAuthenticationService.login(requestDto);

        return ResponseEntity.ok(authenticationAnswer);
    }

    @TrackStatus
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
