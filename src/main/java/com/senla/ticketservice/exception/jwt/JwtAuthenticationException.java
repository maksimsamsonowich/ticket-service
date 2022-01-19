package com.senla.ticketservice.exception.jwt;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String errorMessage) {
        super(errorMessage);
    }

}
