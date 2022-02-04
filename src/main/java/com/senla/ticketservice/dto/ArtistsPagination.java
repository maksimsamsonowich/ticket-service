package com.senla.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArtistsPagination {

    @Min(value = 0)
    @JsonProperty(value = "pageNumber")
    private int pageNumber = 0;

    @Min(value = 5)
    @JsonProperty(value = "pageSize")
    private int pageSize = 10;


}
