package com.senla.ticketservice.entity;

import com.senla.ticketservice.dto.ArtistDto;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artists")
@Accessors(chain = true)
@NamedEntityGraph(
        name = "artists-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("genres"),
                @NamedAttributeNode("cardOwner"),
                @NamedAttributeNode(value = "events", subgraph = "event-sub-graph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "event-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("tickets"),
                                @NamedAttributeNode("eventProgram"),
                                @NamedAttributeNode("location")
                        }
                )
        }

)
@NamedNativeQuery(name = "Artist.findArtistByNickname",
        query = "select * from artists a where a.nickname = :nickname",
        resultSetMapping = "Mapping.ArtistDto")
@SqlResultSetMapping(name = "Mapping.ArtistDto",
        classes = @ConstructorResult(targetClass = ArtistDto.class,
                columns = {@ColumnResult(name = "id"),
                        @ColumnResult(name = "nickname")}))
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "artists_genres", joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @ToString.Exclude
    @OneToMany(mappedBy = "eventOrganizer")
    private Set<Event> events;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User cardOwner;


}
