package com.senla.ticketservice.exception.entity;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String errorMessage) {
        super(errorMessage);
    }

}
