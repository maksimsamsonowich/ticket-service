package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.EventDto;
import com.senla.ticketservice.dto.TicketDto;
import com.senla.ticketservice.entity.Credential;
import com.senla.ticketservice.entity.Event;
import com.senla.ticketservice.entity.Ticket;
import com.senla.ticketservice.entity.User;
import com.senla.ticketservice.mapper.impl.Mapper;
import com.senla.ticketservice.repository.impl.EventRepository;
import com.senla.ticketservice.repository.impl.TicketRepository;
import com.senla.ticketservice.repository.impl.UserRepository;
import com.senla.ticketservice.service.impl.TicketService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TicketServiceTest {

    private Ticket ticketMock;

    @Mock
    private TicketRepository ticketDao;

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketDto expectedResult;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private Mapper<TicketDto, Ticket> ticketMapper;

    @Before
    public void setup() {
        ticketMock = new Ticket()
                .setOrderDate(Date.valueOf("2021-12-03"))
                .setOwner(new User()
                        .setCredential(new Credential()
                                .setEmail("motzisudo@mail.ru")))
                .setEventHolding(new Event()
                        .setId(1L));

        expectedResult = new TicketDto()
                .setEventHolding(new EventDto());
    }

    @Test
    public void createTicketSuccess() {

        Mockito.when(ticketMapper.toEntity(any(), any())).thenReturn(ticketMock);
        Mockito.when(ticketMapper.toDto(any(), any())).thenReturn(expectedResult);
        Mockito.when(userRepository.findByUsername(any()))
                .thenReturn(ticketMock.getOwner());
        Mockito.when(eventRepository.readById(ticketMock.getEventHolding().getId())).thenReturn(ticketMock.getEventHolding());
        Mockito.when(ticketDao.create(ticketMock)).thenReturn(ticketMock);

        TicketDto actualResult = ticketService.createTicket(ticketMock.getOwner().getCredential().getEmail(),
                expectedResult);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readTicketSuccess() {

        Mockito.when(ticketDao.readById(ticketMock.getId())).thenReturn(ticketMock);

        TicketDto expectedResult = ticketMapper.toDto(ticketMock, TicketDto.class);
        TicketDto actualResult = ticketService.readTicket(ticketMock.getId());

        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void updateTicketSuccess() {

        Mockito.when(ticketDao.update(ticketMock)).thenReturn(ticketMock);

        TicketDto expectedResult = new TicketDto();
        TicketDto actualResult = ticketService.update(ticketMock.getId(), expectedResult);

        Assert.assertNull(actualResult);
    }

    @Test
    public void deleteTicketSuccess() {

        doNothing().when(ticketDao).deleteById(ticketMock.getId());
        Mockito.when(ticketDao.readById(ticketMock.getId())).thenReturn(ticketMock);

        ticketService.deleteTicket(ticketMock.getId());

        verify(ticketDao, times(1)).deleteById(ticketMock.getId());
    }

    @Test
    public void getEventTicketsSuccess() {

        Set<Ticket> ticketSetEntityMock = new HashSet<>();

        Mockito.when(ticketDao.getEventTickets(ticketMock.getId())).thenReturn(ticketSetEntityMock);

        Set<TicketDto> expectedResult = ticketMapper.setToDto(ticketSetEntityMock, TicketDto.class);
        Set<TicketDto> actualResult = ticketService.getEventTickets(ticketMock.getId());

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getUserTicketsSuccess() {
        Set<Ticket> ticketSetEntityMock = new HashSet<>();

        Mockito.when(ticketDao.getTicketsByUser(ticketMock.getId())).thenReturn(ticketSetEntityMock);

        Set<TicketDto> expectedResult = ticketMapper.setToDto(ticketSetEntityMock, TicketDto.class);
        Set<TicketDto> actualResult = ticketService.getUserTickets(ticketMock.getId());

        Assert.assertEquals(actualResult, expectedResult);
    }
}
