package com.senla.ticketservice.exception.event;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
