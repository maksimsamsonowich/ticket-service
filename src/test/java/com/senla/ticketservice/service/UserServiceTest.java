package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.UserDto;
import com.senla.ticketservice.entity.User;
import com.senla.ticketservice.mapper.impl.Mapper;
import com.senla.ticketservice.repository.impl.UserRepository;
import com.senla.ticketservice.service.impl.UserService;
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
public class UserServiceTest {

    private User userMock;

    @Mock
    private UserRepository userDao;

    @InjectMocks
    private UserService userService;

    @Mock
    private Mapper<UserDto, User> userMapper;

    @Before
    public void setup() {
        userMock = new User()
                .setFirstName("manoftheyear")
                .setSurname("Max")
                .setFirstName("Max");
    }

    @Test
    public void createUserSuccess() {

        Mockito.when(userDao.create(userMock)).thenReturn(userMock);

        UserDto expectedResult = userMapper.toDto(userMock, UserDto.class);
        UserDto actualResult = userService.createUser(expectedResult);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readUserSuccess() {

        Mockito.when(userDao.readById(userMock.getId()))
                .thenReturn(userMock);

        UserDto expectedResult = userMapper.toDto(userMock, UserDto.class);
        UserDto actualResult = userService.readUser(userMock.getId());

        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void updateUserSuccess() {

        Mockito.when(userDao.update(userMock)).thenReturn(userMock);

        UserDto expectedResult = new UserDto();
        UserDto actualResult = userService.update(userMock.getId(), expectedResult);

        Assert.assertNull(actualResult);
    }

    @Test
    public void deleteUserSuccess() {

        doNothing().when(userDao).deleteById(userMock.getId());

        userService.deleteUser(userMock.getId());

        verify(userDao, times(1)).deleteById(userMock.getId());
    }

}
