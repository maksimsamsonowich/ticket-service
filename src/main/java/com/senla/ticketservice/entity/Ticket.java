package com.senla.ticketservice.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "tickets")
@NamedEntityGraph(
        name = "ticket-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("eventHolding"),
                @NamedAttributeNode("owner")
        }
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private Date orderDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE,
                        CascadeType.REFRESH,
                        CascadeType.DETACH })
    @JoinColumn(name = "event_id")
    private Event eventHolding;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE,
                        CascadeType.REFRESH,
                        CascadeType.DETACH })
    @JoinColumn(name = "user_id")
    private User owner;

}
