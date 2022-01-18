package com.senla.ticketservice.entity;

import com.senla.ticketservice.entity.Artist;
import lombok.experimental.Accessors;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Accessors(chain = true)
@Table(name = "artists_genres")
public class ArtistsGenres {

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;


    public String toString() {
        return "";
    }
}
