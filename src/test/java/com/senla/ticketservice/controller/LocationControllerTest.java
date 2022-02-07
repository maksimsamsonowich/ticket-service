package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.LocationDto;
import com.senla.ticketservice.mapper.impl.JsonMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class LocationControllerTest {

    private String jsonBody;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    private LocationDto locationDto;

    @Autowired
    private LocationController locationController;

    @Before
    public void setup() {


        locationDto = new LocationDto()
                .setAddress("Test")
                .setTitle("NN")
                .setCapacity(12);
    }

    @Test
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ADMIN")
    public void createLocationSuccess() throws Exception {

        this.jsonBody = jsonMapper.toJson(locationDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/location-management")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        CoreMatchers.is(locationDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address",
                        CoreMatchers.is(locationDto.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity",
                        CoreMatchers.is(locationDto.getCapacity())));

    }

    @Test
    @Transactional(readOnly = true)
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ADMIN")
    public void readLocationSuccess() throws Exception {

        LocationDto location = locationController.createLocation(locationDto).getBody();

        this.jsonBody = jsonMapper.toJson(location);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/location-management/{locationId}", location.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        CoreMatchers.is(locationDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address",
                        CoreMatchers.is(locationDto.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity",
                        CoreMatchers.is(locationDto.getCapacity())));

    }

    @Test
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ADMIN")
    public void updateLocationSuccess() throws Exception {

        locationDto.setTitle("test123");

        LocationDto location = locationController.createLocation(locationDto).getBody();

        this.jsonBody = jsonMapper.toJson(location);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/location-management/{locationId}", location.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        CoreMatchers.is(locationDto.getTitle())));
    }

    @Test
    @WithMockUser(username = "fightingdemons@gmail.com", roles = "ADMIN")
    public void deleteLocationSuccess() throws Exception {

        locationDto = locationController.createLocation(locationDto).getBody();

        this.jsonBody = jsonMapper.toJson(locationDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/location-management/{locationId}", locationDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Assert.assertNull(locationController.readLocation(locationDto.getId()).getBody());

    }
}
