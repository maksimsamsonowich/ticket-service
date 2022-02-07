package com.senla.ticketservice.repository;

import com.senla.ticketservice.entity.Location;
import com.senla.ticketservice.repository.impl.LocationRepository;
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

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class LocationDaoTest {

    @Mock
    private Location expectedResult;

    @Resource
    private LocationRepository locationDao;

    @Before
    public void getTestEntity() {
        expectedResult = new Location()
                .setAddress("Кабяка 11")
                .setTitle("NN")
                .setCapacity(12);
    }

    @Test
    public void createLocationSuccess() {
        expectedResult = locationDao.create(expectedResult);

        Location actualResult = locationDao.readById(expectedResult.getId());

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void deleteLocationSuccess() {
        expectedResult = locationDao.create(expectedResult);

        locationDao.deleteById(expectedResult.getId());

        Location actualResult = locationDao.readById(expectedResult.getId());
        Assert.assertNull(actualResult);
    }

    @Test
    public void readLocationSuccess() {
        expectedResult = locationDao.create(expectedResult);

        Location actualResult = locationDao.readById(expectedResult.getId());

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void updateLocationSuccess() {
        expectedResult = locationDao.create(expectedResult);

        expectedResult.setTitle("KK");
        locationDao.update(expectedResult);

        Location actualResult = locationDao.readById(expectedResult.getId());

        Assert.assertEquals(actualResult, expectedResult);
    }

}
