package com.senla.ticketservice.entity;

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
@Table(name = "locations")
@Accessors(chain = true)
@NamedEntityGraph(
        name = "location-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "events", subgraph = "events-sub-graph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "events-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("eventProgram"),
                                @NamedAttributeNode("eventOrganizer")
                        }
                )
        }
)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String address;

    private int capacity;

    @ToString.Exclude
    @OneToMany(mappedBy = "location",
            fetch = FetchType.LAZY)
    private Set<Event> events;

}
