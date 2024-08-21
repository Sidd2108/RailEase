package com.rail.railEase.controller;

import com.rail.railEase.dto.BookTicketDto;
import com.rail.railEase.dto.LoginRequest;
import com.rail.railEase.dto.TicketResponseDto;
import com.rail.railEase.dto.UserDto;
import com.rail.railEase.exception.InsufficientBalanceException;
import com.rail.railEase.exception.InvalidCredentials;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.exception.UserAlreadyExists;
import com.rail.railEase.model.Users;
import com.rail.railEase.service.Users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "http://localhost:4200") // Allow CORS requests from this origin
public class UsersController {

    @Autowired
    private UsersService service;

    @PostMapping(value = "/register")
    ResponseEntity<Object> registerUser(@RequestBody Users user) {
        if(user.getBalance()<0){
            return new ResponseEntity<>("Balance can't be less than zero", HttpStatus.BAD_REQUEST);
        }
        try{
            Users res = service.registerUser(user);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserAlreadyExists | InvalidCredentials e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/login")
    ResponseEntity<Object> loginUser(@RequestBody LoginRequest user) {
        try{
            Users res = service.loginUser(user);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (InvalidCredentials e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/updateProfile")
    ResponseEntity<Object> updateProfile(@RequestBody UserDto user) {
        try{
            Users res = service.updateUser(user);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ResourceNotFoundException | InvalidCredentials e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/book-ticket")
    ResponseEntity<Object> bookTicket(@RequestBody BookTicketDto bookDto) {
        try{
            String ticketDetails = service.bookTicket(bookDto.getRouteId(), bookDto.getEmail());
            TicketResponseDto responseDto = parseTicketDetails(ticketDetails);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (InsufficientBalanceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED); // Use a more appropriate status code
        }
    }

    private TicketResponseDto parseTicketDetails(String ticketDetails) {
        TicketResponseDto dto = new TicketResponseDto();
        // Parse the string and populate the DTO fields
        // You might need to use regular expressions or split by new lines and colons

        // Example (simple parsing logic):
        String[] lines = ticketDetails.split("\n");
        dto.setRouteId(lines[0].split(": ")[1]);
        dto.setDepartureStation(lines[1].split(": ")[1]);
        dto.setArrivalStation(lines[2].split(": ")[1]);
        dto.setAmount(Double.valueOf(lines[3].split(": ")[1]));
        dto.setPaymentTime(lines[4].split(": ")[1]);
        dto.setPaymentStatus(lines[5].split(": ")[1]);
        dto.setIssuedTo(lines[6].split(": ")[1]);

        return dto;
    }

}
