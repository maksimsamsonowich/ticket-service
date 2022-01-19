package com.senla.ticketservice.exception.credential;

public class CredentialNotFoundException extends RuntimeException {

    public CredentialNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
