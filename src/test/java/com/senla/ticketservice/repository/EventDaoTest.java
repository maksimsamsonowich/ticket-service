package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Event;
import com.senla.ticketservice.entity.Location;
import com.senla.ticketservice.filter.EventFilterDto;
import com.senla.ticketservice.repository.impl.EventRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class EventDaoTest {

    @Resource
    private EventRepository eventDao;

    @Mock
    private Event expectedResult;

    @Mock
    private Location locationEntity;

    @Before
    public void getTestEntity() {
        expectedResult = new Event()
                .setTitle("Title")
                .setDescription("Desc")
                .setAgeLimit((short) 18)
                .setOccupiedPlace((short) 11)
                .setDate(Date.valueOf("2021-11-29"));

        locationEntity = new Location()
                .setId(1L);
    }

    @Test
    public void createEventSuccess() {
        expectedResult = eventDao.create(expectedResult);

        Event actualResult = eventDao.readById(expectedResult.getId());

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void deleteEventSuccess() {
        expectedResult = eventDao.create(expectedResult);

        eventDao.deleteById(expectedResult.getId());

        Event actualResult = eventDao.readById(expectedResult.getId());
        Assert.assertNull(actualResult);
    }

    @Test
    public void readEventSuccess() {
        expectedResult = eventDao.create(expectedResult);

        Event actualResult = eventDao.readById(expectedResult.getId());

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void updateEventSuccess() {
        expectedResult = eventDao.create(expectedResult);

        expectedResult.setAgeLimit((short) 20);
        eventDao.update(expectedResult);

        Event actualResult = eventDao.readById(expectedResult.getId());

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getEventByLocationSuccess() {
        Set<Event> events = eventDao.getEventsByLocation(locationEntity.getId());
        Event eventMock = eventDao.readById(1L);

        Set<Event> eventSetMock = new HashSet<>();
        eventSetMock.add(eventMock);

        Assert.assertEquals(events, eventSetMock);
    }

    @Test
    public void getAllEventsSuccess() {
        List<Event> actualResult = eventDao.getAll(new EventFilterDto().setPageSize(1));

        long step = 1;
        List<Event> expectedResult = new ArrayList<Event>();

        while (true) {
            Event event = eventDao.readById(step);

            if (event == null) {
                break;
            }

            expectedResult.add(event);
            step += 1;
        }

        Assert.assertEquals(expectedResult, actualResult);
    }

}
