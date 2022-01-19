package com.senla.ticketservice.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@Accessors(chain = true)
public abstract class PaginationDto {

    @Min(value = 0)
    @JsonProperty(value = "pageNumber")
    private int pageNumber = 0;

    @Min(value = 5)
    @JsonProperty(value = "pageSize")
    private int pageSize = 10;

}
