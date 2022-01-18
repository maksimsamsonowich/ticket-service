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
@Table(name = "creds")
@Accessors(chain = true)
@NamedEntityGraph(
        name = "credential-graph",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("roles")
        }
)
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @OneToOne(mappedBy = "credential", fetch = FetchType.LAZY,
                cascade = { CascadeType.ALL })
    private User user;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "cred_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
