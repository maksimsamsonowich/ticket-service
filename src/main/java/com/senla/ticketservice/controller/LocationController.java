package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.LocationDto;
import com.senla.ticketservice.metamodel.Roles;
import com.senla.ticketservice.service.ILocationService;
import eu.senla.customlibrary.trackstatus.TrackStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("location-management")
public class LocationController {

    private final ILocationService iLocationService;

    @TrackStatus
    @PostMapping
    @Secured(Roles.ADMIN)
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(iLocationService.createLocation(locationDto));
    }

    @TrackStatus
    @PreAuthorize("permitAll")
    @GetMapping("{locationId}")
    public ResponseEntity<LocationDto> readLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(iLocationService.readLocation(locationId));
    }

    @TrackStatus
    @Secured(Roles.ADMIN)
    @PutMapping("{locationId}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Long locationId, @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(iLocationService.update(locationId, locationDto));
    }

    @TrackStatus
    @Secured(Roles.ADMIN)
    @DeleteMapping("{locationId}")
    public void deleteLocation(@PathVariable("locationId") Long locationId) {
        iLocationService.deleteLocation(locationId);
    }

    @TrackStatus
    @PreAuthorize("permitAll")
    @GetMapping("by-event/{eventId}")
    public ResponseEntity<LocationDto> getEventLocation(@PathVariable Long eventId) {
        return ResponseEntity.ok(iLocationService.getEventLocation(eventId));
    }
}

