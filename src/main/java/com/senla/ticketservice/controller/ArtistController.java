package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.ArtistDto;
import com.senla.ticketservice.dto.StatusDto;
import com.senla.ticketservice.metamodel.Roles;
import com.senla.ticketservice.service.IArtistService;
import com.senla.ticketservice.service.IItemsSecurityExpressions;
import eu.senla.customlibrary.trackstatus.TrackStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("artist-management")
@Tag(name = "Artist controller", description = "Controller witch control the artists")
public class ArtistController {

    private final IArtistService iArtistService;

    @TrackStatus
    @Secured(Roles.ADMIN)
    @PostMapping("{id}")
    @Operation(summary = "Create Artist", description = "Method to create artist card")
    public ResponseEntity<StatusDto> createArtistCard(@PathVariable("id") Long userId,
                                                      @RequestBody ArtistDto artistDto) {

        iArtistService.createArtist(userId, artistDto);

        return ResponseEntity.ok(new StatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setMessage("Success"));
    }

    @TrackStatus
    @GetMapping("{id}")
    @PreAuthorize("permitAll")
    public ResponseEntity<ArtistDto> readArtist(@PathVariable("id") Long artistId) {

        ArtistDto currentArtist = iArtistService.getArtist(artistId);

        return ResponseEntity.ok(currentArtist);
    }

    @TrackStatus
    @PutMapping("{id}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedArtistCard(#artistId, authentication)")
    public ResponseEntity<ArtistDto> updateArtist(@PathVariable("id") Long artistId,
                                                  @RequestBody ArtistDto artistDto) {
        ArtistDto currentArtist = iArtistService.updateArtist(artistId, artistDto);

        return ResponseEntity.ok(currentArtist);
    }

    @TrackStatus
    @DeleteMapping("{id}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedArtistCard(#artistId, authentication)")
    public ResponseEntity<StatusDto> deleteArtist(@PathVariable("id") Long artistId) {
        iArtistService.deleteArtist(artistId);

        return ResponseEntity.ok(new StatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setMessage("Success"));
    }

    @TrackStatus
    @PreAuthorize("permitAll")
    @GetMapping("by-event/{id}")
    public ResponseEntity<ArtistDto> getArtistByEventId(@PathVariable("id") Long eventId) {
        ArtistDto currentArtist = iArtistService.getArtistByEventId(eventId);

        return ResponseEntity.ok(currentArtist);
    }

}
