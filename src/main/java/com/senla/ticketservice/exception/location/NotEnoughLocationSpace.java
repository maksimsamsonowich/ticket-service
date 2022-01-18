package com.senla.ticketservice.exception.location;

public class NotEnoughLocationSpace extends RuntimeException {

    public NotEnoughLocationSpace(String errorMessage) {
        super(errorMessage);
    }

}
