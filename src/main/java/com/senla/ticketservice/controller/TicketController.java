package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.TicketDto;
import com.senla.ticketservice.metamodel.Roles;
import com.senla.ticketservice.service.IItemsSecurityExpressions;
import com.senla.ticketservice.service.ITicketService;
import eu.senla.customlibrary.trackstatus.TrackStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("ticket-management")
public class TicketController {

    private final ITicketService iTicketService;

    private final IItemsSecurityExpressions iItemsSecurityExpressions;

    @TrackStatus
    @PostMapping
    @Secured(Roles.USER)
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto) {
        final String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        TicketDto returnValue = iTicketService.createTicket(currentEmail, ticketDto);

        return ResponseEntity.ok(returnValue);
    }

    @TrackStatus
    @GetMapping("{ticketId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedTicket(#ticketId, authentication)")
    public ResponseEntity<TicketDto> readTicket(@PathVariable Long ticketId) {

        TicketDto returnValue = iTicketService.readTicket(ticketId);

        return ResponseEntity.ok(returnValue);
    }

    @TrackStatus
    @PutMapping("{ticketId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedTicket(#ticketId, authentication)")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long ticketId, @RequestBody TicketDto ticketDto) {
        TicketDto returnValue = iTicketService.update(ticketId, ticketDto);

        return ResponseEntity.ok(returnValue);
    }

    @TrackStatus
    @DeleteMapping("{ticketId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedTicket(#ticketId, authentication)")
    public void deleteTicket(@PathVariable Long ticketId) {
        iTicketService.deleteTicket(ticketId);
    }

    @TrackStatus
    @PreAuthorize("permitAll")
    @GetMapping("by-event/{eventId}")
    public ResponseEntity<Set<TicketDto>> getEventTickets(@PathVariable Long eventId) {
        return ResponseEntity.ok(iTicketService.getEventTickets(eventId));
    }

    @TrackStatus
    @PreAuthorize("permitAll")
    @GetMapping("by-user/{userId}")
    public ResponseEntity<Set<TicketDto>> getUserTickets(@PathVariable Long userId) {
        return ResponseEntity.ok(iTicketService.getUserTickets(userId));
    }

}
