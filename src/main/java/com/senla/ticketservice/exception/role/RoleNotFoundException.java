package com.senla.ticketservice.exception.role;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
