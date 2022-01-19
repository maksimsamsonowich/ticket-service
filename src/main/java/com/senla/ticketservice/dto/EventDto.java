package com.senla.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "ageLimit")
    private int ageLimit;

    @JsonProperty(value = "occupiedPlace")
    private int occupiedPlace;

    @JsonProperty(value = "date")
    private Date date;

    @JsonProperty(value = "eventOrganizer")
    private EventArtistDto eventOrganizer;

    @JsonProperty(value = "eventProgram")
    private EventProgramDto eventProgram;

}
