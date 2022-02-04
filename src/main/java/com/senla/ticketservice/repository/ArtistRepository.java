package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Artist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

    @Query(value = "select a from events e " +
            "left join artists a " +
            "on e.artists_id = a.id " +
            "where a.id = :artist_id " +
            "limit 1", nativeQuery = true)
    Artist getArtistByEventsId(@Param("artist_id") Long id);

    @Query(name = "Artist.findArtistByNickname", nativeQuery = true)
    Artist getArtistByNickname(@Param("nickname") String nickname);

    List<Artist> findAll(Pageable pagination);

}
