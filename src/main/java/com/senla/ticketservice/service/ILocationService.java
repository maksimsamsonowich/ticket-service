package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.LocationDto;

public interface ILocationService {

    LocationDto createLocation(LocationDto locationDto);

    LocationDto readLocation(Long id);

    LocationDto readLocationUsingRestTemplate(Long locationId);

    LocationDto update(Long id, LocationDto locationDto);

    void deleteLocation(Long id);

    LocationDto getEventLocation(Long id);

}
