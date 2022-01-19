package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.EventDto;
import com.senla.ticketservice.filter.PaginationDto;

import java.util.List;
import java.util.Set;

public interface IEventService {

    EventDto createEvent(EventDto eventDto);

    EventDto readEvent(Long  id);

    EventDto update(Long id, EventDto eventDto);

    void deleteEvent(Long id);

    Set<EventDto> getEventsByLocation(Long id);

    List<EventDto> getAllEvents(PaginationDto additionalProperties);

}
