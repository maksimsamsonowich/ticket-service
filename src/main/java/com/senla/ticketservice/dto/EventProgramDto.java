package com.senla.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventProgramDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "continuance")
    private Time continuance;

    @JsonProperty(value = "date")
    private Date date;

    @JsonProperty(value = "price")
    private double price;

}
