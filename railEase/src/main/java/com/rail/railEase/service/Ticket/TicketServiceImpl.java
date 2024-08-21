package com.rail.railEase.service.Ticket;

import com.rail.railEase.constant.PaymentStatus;
import com.rail.railEase.dto.TicketDto;
import com.rail.railEase.exception.InsufficientBalanceException;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.model.Route;
import com.rail.railEase.model.Ticket;
import com.rail.railEase.model.Users;
import com.rail.railEase.repository.TicketRepo;
import com.rail.railEase.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.rail.railEase.logger.LogClient.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private UsersRepo userrepo;

    @Override
    public String createTicket(Optional<Route> routeOpt, Optional<Users> userOpt) throws InsufficientBalanceException {
        Route route = routeOpt
                .orElseThrow(()-> new ResourceNotFoundException("Route cannot be null."));
        Users user = userOpt
                .orElseThrow(()-> new ResourceNotFoundException("User cannot be null."));
        double totalFare = route.getFare();
        if(user.getBalance()<totalFare){
            throw new InsufficientBalanceException("Your current account balance is Rs."+user.getBalance()+"\nPlease consider depositing additional funds to book your tickets seamlessly.");
        }
        user.setBalance((long) (user.getBalance()-totalFare));
        Ticket ticket = new Ticket();
        ticket.setRoute(route);
        ticket.setAmount(totalFare);
        ticket.setUser(user);
        ticket.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setPaymentTime(LocalDateTime.now());

        String ticketString = generateTicketString(route, user, ticket.getPaymentStatus()); // Optional: store the ticket format if needed

        // Save the ticket to the repository
        ticketRepo.save(ticket);
        logger.info("Ticket generated with id : {} for user with id : {}", ticket.getTicketId(), user.getUserId());

        return ticketString;
    }
    private String generateTicketString(Route route, Users user, PaymentStatus paymentStatus) {
        StringBuilder sb = new StringBuilder();

//        sb.append("----- TICKET DETAILS -----\n");
        sb.append("Route ID: ").append(route.getRouteId()).append("\n");
        sb.append("Departure Station: ").append(route.getDepartureStation().getStationName()).append("\n");
        sb.append("Arrival Station: ").append(route.getArrivalStation().getStationName()).append("\n");
        sb.append("Amount: ").append(String.format("%.2f", route.getFare())).append("\n");
        sb.append("Payment Time: ").append(LocalDateTime.now()).append("\n");
        sb.append("Payment Status: ").append(paymentStatus).append("\n");
        sb.append("Issued to : ").append(user.getUsername()).append("\n\n");
//        sb.append("----- JOURNEY SHOULD COMMENCE WITHIN 1 HOUR  -----\n");

        return sb.toString();
    }

    @Override
    public List<TicketDto> getTicketsForUser(Integer user_id) throws ResourceNotFoundException{
        if(!userrepo.existsById(user_id)){
            throw new ResourceNotFoundException("User with id : "+ user_id+" not found");
        }
        List<TicketDto> tickets = ticketRepo.findAllTickets(user_id);
        return tickets;
    }
}
