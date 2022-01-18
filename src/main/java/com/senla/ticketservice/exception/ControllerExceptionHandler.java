package com.senla.ticketservice.exception;

import com.senla.ticketservice.dto.ErrorDto;
import com.senla.ticketservice.exception.artist.ArtistNotFoundException;
import com.senla.ticketservice.exception.event.EventNotFoundException;
import com.senla.ticketservice.exception.location.LocationNotFoundException;
import com.senla.ticketservice.exception.location.NotEnoughLocationSpace;
import com.senla.ticketservice.exception.role.RoleNotFoundException;
import com.senla.ticketservice.exception.ticket.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({
            TicketNotFoundException.class,
            RoleNotFoundException.class,
            LocationNotFoundException.class,
            EntityNotFoundException.class,
            ArtistNotFoundException.class,
            EventNotFoundException.class
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<ErrorDto> resourceNotFoundException(RuntimeException exception) {
        return ResponseEntity.ok(new ErrorDto()
                .setStatusCode(HttpStatus.NO_CONTENT.value())
                .setErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({
            NotEnoughLocationSpace.class
    })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDto> conflictWithResource(RuntimeException exception) {
        return ResponseEntity.ok(new ErrorDto()
                .setStatusCode(HttpStatus.CONFLICT.value())
                .setErrorMessage(exception.getMessage()));
    }

}
