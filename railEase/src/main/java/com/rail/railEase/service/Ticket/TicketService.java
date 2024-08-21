package com.rail.railEase.service.Ticket;

import com.rail.railEase.dto.TicketDto;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.model.Route;
import com.rail.railEase.model.Ticket;
import com.rail.railEase.model.Users;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    String createTicket(Optional<Route>  route, Optional<Users> user);

    List<TicketDto> getTicketsForUser(Integer user_id) throws ResourceNotFoundException;
}
