package com.senla.ticketservice.controller;

import com.senla.ticketservice.TicketServiceApplication;
import com.senla.ticketservice.dto.EventArtistDto;
import com.senla.ticketservice.dto.EventDto;
import com.senla.ticketservice.dto.LocationDto;
import com.senla.ticketservice.mapper.impl.JsonMapper;
import io.jsonwebtoken.lang.Assert;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = TicketServiceApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class EventControllerTest {

    private String jsonBody;

    @Autowired
    private MockMvc mockMvc;

    private EventDto eventDto;

    private LocationDto locationDto;

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private EventController eventController;

    @Before
    public void setup() {
        eventDto = new EventDto()
                .setTitle("Title")
                .setDescription("Desc")
                .setAgeLimit((short) 18)
                .setOccupiedPlace((short) 11)
                .setDate(Date.valueOf("2021-11-29"))
                .setEventOrganizer(new EventArtistDto().setId(1L));

        locationDto = new LocationDto().setId(1L);
    }

    @Test
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ARTIST")
    public void createEventSuccess() throws Exception {

        this.jsonBody = jsonMapper.toJson(eventDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/event-management")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        CoreMatchers.is(eventDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        CoreMatchers.is(eventDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ageLimit",
                        CoreMatchers.is(eventDto.getAgeLimit())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupiedPlace",
                        CoreMatchers.is(eventDto.getOccupiedPlace())));
    }

    @Test
    @Transactional(readOnly = true)
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ARTIST")
    public void readEventSuccess() throws Exception {

        eventDto = eventController.createEvent(eventDto).getBody();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/event-management/{eventId}", eventDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        CoreMatchers.is(eventDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        CoreMatchers.is(eventDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ageLimit",
                        CoreMatchers.is(eventDto.getAgeLimit())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupiedPlace",
                        CoreMatchers.is(eventDto.getOccupiedPlace())));

    }

    @Test
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ARTIST")
    public void updateEventSuccess() throws Exception {

        eventDto = eventController.createEvent(eventDto).getBody();
        eventDto.setTitle("Edited title");

        this.jsonBody = jsonMapper.toJson(eventDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/event-management/{eventId}", eventDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        CoreMatchers.is(eventDto.getTitle())));
    }

    @Test
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ARTIST")
    public void deleteEventSuccess() throws Exception {

        eventDto = eventController.createEvent(eventDto).getBody();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/event-management/{eventId}", eventDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.isNull(eventController.readEvent(eventDto.getId()).getBody());

    }

    @Test
    @Transactional(readOnly = true)
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ADMIN")
    public void getEventsByLocationSuccess() throws Exception{

        eventDto = Objects.requireNonNull(eventController.getEventsByLocation(locationDto.getId())
                .getBody()).iterator().next();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/event-management/by-location/{locationId}", locationDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title",
                        CoreMatchers.is(eventDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description",
                        CoreMatchers.is(eventDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].ageLimit",
                        CoreMatchers.is((int) eventDto.getAgeLimit())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].occupiedPlace",
                        CoreMatchers.is((int) eventDto.getOccupiedPlace())));
    }
}
