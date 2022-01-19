package com.senla.ticketservice.service.impl;

import com.senla.ticketservice.dto.LocationDto;
import com.senla.ticketservice.entity.Location;
import com.senla.ticketservice.mapper.IMapper;
import com.senla.ticketservice.repository.impl.LocationRepository;
import com.senla.ticketservice.service.ILocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;

    private final IMapper<LocationDto, Location> locationMapper;

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location currentLocation = locationMapper.toEntity(locationDto, Location.class);

        locationRepository.create(currentLocation);

        return locationMapper.toDto(currentLocation, LocationDto.class);
    }

    @Override
    public LocationDto readLocation(Long locationId) {
        return locationMapper.toDto(locationRepository.readById(locationId), LocationDto.class);
    }

    @Override
    public LocationDto update(Long locationId, LocationDto locationDto) {
        locationDto.setId(locationId);

        Location currentLocation = locationMapper.toEntity(locationDto, Location.class);

        locationRepository.update(currentLocation);

        return locationMapper.toDto(currentLocation, LocationDto.class);
    }

    @Override
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    @Override
    @Transactional(readOnly = true)
    public LocationDto getEventLocation(Long locationId) {
        return locationMapper.toDto(locationRepository.getLocationByEvent(locationId), LocationDto.class);
    }

}
