package com.senla.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserTicketsDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "surname")
    private String surname;

}
