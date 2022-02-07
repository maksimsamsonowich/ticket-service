package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.LocationDto;
import com.senla.ticketservice.entity.Location;
import com.senla.ticketservice.mapper.impl.Mapper;
import com.senla.ticketservice.repository.impl.LocationRepository;
import com.senla.ticketservice.service.impl.LocationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocationServiceTest {

    private Location locationMock;

    @Mock
    private LocationRepository locationDao;

    @InjectMocks
    private LocationService locationService;

    @Mock
    private Mapper<LocationDto, Location> locationMapper;

    @Before
    public void setup() {
        locationMock = new Location()
                .setTitle("BAR")
                .setCapacity(50)
                .setAddress("FFF");
    }

    @Test
    public void createLocationSuccess() {

        Mockito.when(locationDao.create(locationMock)).thenReturn(locationMock);

        LocationDto expectedResult = locationMapper.toDto(locationMock, LocationDto.class);
        LocationDto actualResult = locationService.createLocation(expectedResult);

        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void readLocationSuccess() {

        Mockito.when(locationDao.readById(locationMock.getId())).thenReturn(locationMock);

        LocationDto expectedResult = locationMapper.toDto(locationMock, LocationDto.class);
        LocationDto actualResult = locationService.readLocation(locationMock.getId());

        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void updateLocationSuccess() {

        Mockito.when(locationDao.update(locationMock)).thenReturn(locationMock);

        LocationDto expectedResult = new LocationDto();
        LocationDto actualResult = locationService.update(locationMock.getId(), expectedResult);

        Assert.assertNull(actualResult);

    }

    @Test
    public void deleteLocationSuccess() {

        doNothing().when(locationDao).deleteById(locationMock.getId());
        Mockito.when(locationDao.readById(locationMock.getId())).thenReturn(locationMock);

        locationService.deleteLocation(locationMock.getId());

        verify(locationDao, times(1)).deleteById(locationMock.getId());
    }

    @Test
    public void getEventLocationSuccess() {

        Mockito.when(locationDao.getLocationByEvent(locationMock.getId())).thenReturn(locationMock);

        LocationDto expectedResult = locationMapper.toDto(locationMock, LocationDto.class);
        LocationDto actualResult = locationService.getEventLocation(locationMock.getId());

        Assert.assertEquals(expectedResult, actualResult);
    }
}
