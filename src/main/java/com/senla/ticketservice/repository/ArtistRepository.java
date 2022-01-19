package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

    Artist getArtistByEventsId(Long id);

}
