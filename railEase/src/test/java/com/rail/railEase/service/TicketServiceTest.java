package com.rail.railEase.service;

import com.rail.railEase.constant.PaymentStatus;
import com.rail.railEase.dto.TicketDto;
import com.rail.railEase.model.Route;
import com.rail.railEase.model.Station;
import com.rail.railEase.model.Ticket;
import com.rail.railEase.model.Users;
import com.rail.railEase.repository.TicketRepo;
import com.rail.railEase.repository.UsersRepo;
import com.rail.railEase.service.Ticket.TicketServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= {TicketRepo.class, UsersRepo.class})
public class TicketServiceTest {

    @Mock
    private TicketRepo ticketRepo;

    @Mock
    private UsersRepo usersRepo;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    @Order(1)
    public void testGetTicketsForUsers(){
        List<TicketDto> tickets = new ArrayList<>();
        Users user = new Users(1,"Riya","riya123","riya@gmailcom","9920993837", LocalDateTime.now(), 100L);
//        Station station1 = new Station(1,"Vashi",LocalDateTime.now(),'H', 'H', 25.0);
//        Station station2 = new Station(2,"Thane",LocalDateTime.now(),'C', 'C', 25.0);
//        Route route = new Route(1, station1, station2,LocalDateTime.now(), 50);

        // for delete doNothing

        TicketDto ticket = new TicketDto(1,1,1,"Vashi","Thane",LocalDateTime.now());
        tickets.add(ticket);
        when(ticketRepo.findAllTickets(user.getUserId())).thenReturn(tickets);
        when(usersRepo.existsById(user.getUserId())).thenReturn(true);

        List<TicketDto> result = ticketService.getTicketsForUser(user.getUserId());

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(ticket);
        assertThat(tickets.get(0).getUser_id().equals(1));

    }
}
