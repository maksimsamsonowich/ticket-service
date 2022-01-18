package com.senla.ticketservice.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@NamedEntityGraph(
        name = "events-program-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "event", subgraph = "event-sub-graph")
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
@Table(name = "events_program")
public class EventProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Time continuance;

    private double price;

    @ToString.Exclude
    @OneToOne(mappedBy = "eventProgram", fetch = FetchType.LAZY)
    private Event event;

}
