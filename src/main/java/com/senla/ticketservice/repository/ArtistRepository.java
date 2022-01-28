package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

    Artist getArtistByEventsId(Long id);

}
