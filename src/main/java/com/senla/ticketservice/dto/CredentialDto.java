package com.senla.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CredentialDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "roles")
    private Set<RoleDto> roles;

}
