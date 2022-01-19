package com.senla.ticketservice.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.ticketservice.filter.fields.types.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EventFilterDto extends PaginationDto {

    @JsonProperty(value = "byOccupiedPlace")
    private ByOccupiedPlace byOccupiedPlace = new ByOccupiedPlace();

    @JsonProperty(value = "byPrice")
    private ByPrice byPrice = new ByPrice();

    @JsonProperty(value = "byDate")
    private ByDate byDate = new ByDate();

    @JsonProperty(value = "byEventOrganizer")
    private ByEventOrganizer byEventOrganizer = new ByEventOrganizer();

    @JsonProperty(value = "byAgeLimit")
    private ByAgeLimit byAgeLimit = new ByAgeLimit();

}
