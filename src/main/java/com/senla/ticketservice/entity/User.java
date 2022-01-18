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
@Table(name = "users")
@Accessors(chain = true)
@NamedEntityGraph(
        name = "user-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("tickets"),
                @NamedAttributeNode("credential")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @ToString.Exclude
    @OneToMany(mappedBy = "cardOwner")
    private Set<Artist> artistCard;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner")
    private Set<Ticket> tickets;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "creds_id")
    private Credential credential;

    @Override
    public boolean equals(Object object) {
        User user = (User) object;
        return id == user.getId();
    }

}
