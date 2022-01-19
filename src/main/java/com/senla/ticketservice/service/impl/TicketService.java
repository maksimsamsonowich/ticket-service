package com.senla.ticketservice.service.impl;

import com.senla.ticketservice.dto.TicketDto;
import com.senla.ticketservice.entity.Event;
import com.senla.ticketservice.entity.Ticket;
import com.senla.ticketservice.exception.location.NotEnoughLocationSpace;
import com.senla.ticketservice.mapper.IMapper;
import com.senla.ticketservice.repository.impl.EventRepository;
import com.senla.ticketservice.repository.impl.TicketRepository;
import com.senla.ticketservice.repository.impl.UserRepository;
import com.senla.ticketservice.service.ITicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    private final IMapper<TicketDto, Ticket> ticketMapper;

    @Override
    public TicketDto createTicket(String email, TicketDto ticketDto) {

        Event currentEvent = eventRepository.readById(ticketDto.getEventHolding().getId());

        if (currentEvent.getOccupiedPlace() + 1 > currentEvent.getLocation().getCapacity())
            throw new NotEnoughLocationSpace("Sorry, but all tickets are sold out.");

        currentEvent.setOccupiedPlace((short) (currentEvent.getOccupiedPlace() + ((short) 1)));

        Ticket currentTicket = ticketMapper.toEntity(ticketDto, Ticket.class)
                .setOwner(userRepository.findByUsername(email))
                .setEventHolding(eventRepository.readById(ticketDto.getEventHolding().getId()));

        currentTicket = ticketRepository.create(currentTicket);
        eventRepository.update(currentEvent);

        log.info("Ticket " + currentTicket.getId() + " created and Event " + currentEvent.getId() + " updated.");

        return ticketMapper.toDto(currentTicket, TicketDto.class);
    }

    @Override
    public TicketDto readTicket(Long ticketId) {
        return ticketMapper.toDto(ticketRepository.readById(ticketId), TicketDto.class);
    }

    @Override
    public TicketDto update(Long id, TicketDto ticketDto) {
        Ticket currentTicket = ticketMapper.toEntity(ticketDto, Ticket.class);

        ticketRepository.update(currentTicket);

        log.info("Ticket " + id + " updated.");

        return ticketMapper.toDto(currentTicket, TicketDto.class);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);

        log.info("Ticket " + id + " deleted.");
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TicketDto> getEventTickets(Long id) {
        return ticketMapper.setToDto(ticketRepository.getEventTickets(id), TicketDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TicketDto> getUserTickets(Long id) {
        return ticketMapper.setToDto(ticketRepository.getTicketsByUser(id), Ticket.class);
    }


}
