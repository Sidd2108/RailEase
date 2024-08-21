package com.rail.railEase.controller;

import com.rail.railEase.dto.TicketDto;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.model.Ticket;
import com.rail.railEase.service.Ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/getAllTickets/{user_id}")
    public ResponseEntity<Object> getAllTickets(@PathVariable Integer user_id) {
        try{
            List<TicketDto> res = ticketService.getTicketsForUser(user_id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
