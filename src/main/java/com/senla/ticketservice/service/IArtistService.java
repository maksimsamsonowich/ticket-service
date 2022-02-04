package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.ArtistDto;
import com.senla.ticketservice.dto.ArtistsPagination;

import java.util.List;

public interface IArtistService {

    void createArtist(Long userId, ArtistDto artistDto);

    ArtistDto getArtist(Long artistId);

    ArtistDto updateArtist(Long artistId, ArtistDto artistDto);

    void deleteArtist(Long artistId);

    ArtistDto getArtistByEventId(Long eventId);

    List<ArtistDto> findAllArtistsWithPagination(ArtistsPagination pagination);
}
