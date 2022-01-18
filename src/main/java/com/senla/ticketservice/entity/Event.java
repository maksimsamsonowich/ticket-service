package com.senla.ticketservice.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
@Accessors(chain = true)
@NamedEntityGraph(
        name = "event-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("tickets"),
                @NamedAttributeNode("eventProgram"),
                @NamedAttributeNode("location"),
                @NamedAttributeNode(value = "eventOrganizer", subgraph = "artist-sub-graph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "artist-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("cardOwner"),
                                @NamedAttributeNode("genres")
                        }
                )
        }
)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "age_limit", nullable = false)
    private Short ageLimit;

    @Column(name = "occupied_places", nullable = false)
    private Short occupiedPlace;

    @Column(name = "date", nullable = false)
    private Date date;

    @ToString.Exclude
    @OneToMany(mappedBy = "eventHolding",
            fetch = FetchType.LAZY)
    private Set<Ticket> tickets;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE, CascadeType.DETACH })
    @JoinColumn(name = "artists_id")
    private Artist eventOrganizer;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_program_id")
    private EventProgram eventProgram;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locations_id")
    private Location location;

    @Override
    public boolean equals(Object object) {
        Event event = (Event) object;
        return id == event.getId();
    }

}
