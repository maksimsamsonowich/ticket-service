package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.AuthenticationAnswerDto;
import com.senla.ticketservice.dto.AuthenticationRequestDto;
import com.senla.ticketservice.dto.CredentialDto;
import com.senla.ticketservice.service.IAuthenticationService;
import com.senla.ticketservice.service.ICredentialService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @Value("${rabbitmq.exchange}")
    private String rabbitExchange;

    @Value("${rabbitmq.routingKey}")
    private String rabbitRoutingKey;

    private final RabbitAdmin rabbitAdmin;

    private final RabbitTemplate rabbitTemplate;

    private final ICredentialService iCredentialService;

    private final IAuthenticationService iAuthenticationService;

    @PostMapping("auth")
    @PreAuthorize("permitAll")
    public ResponseEntity<AuthenticationAnswerDto> customerAuthentication
            (@RequestBody AuthenticationRequestDto requestDto) {
        log.info("Authentication controller received the post request (customerAuthentication).");

        AuthenticationAnswerDto authenticationAnswer =
                iAuthenticationService.login(requestDto);

        rabbitTemplate.convertAndSend(rabbitExchange, rabbitRoutingKey,
                requestDto.getEmail());

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

    @PostConstruct
    public void init() {
        rabbitAdmin.initialize();
    }

}
