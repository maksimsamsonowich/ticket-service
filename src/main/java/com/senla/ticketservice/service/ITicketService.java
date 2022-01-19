package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.TicketDto;

import java.util.Set;

public interface ITicketService {

    TicketDto createTicket(String email, TicketDto ticketDto);

    TicketDto readTicket(Long id);

    TicketDto update(Long id, TicketDto ticketDto);

    void deleteTicket(Long id);

    Set<TicketDto>getEventTickets(Long id);

    Set<TicketDto> getUserTickets(Long id);

}
