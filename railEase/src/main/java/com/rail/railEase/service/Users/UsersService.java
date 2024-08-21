package com.rail.railEase.service.Users;

import com.rail.railEase.dto.LoginRequest;
import com.rail.railEase.dto.UserDto;
import com.rail.railEase.exception.InvalidCredentials;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.exception.UserAlreadyExists;
import com.rail.railEase.model.Users;


public interface UsersService {
    Users registerUser(Users user) throws UserAlreadyExists, InvalidCredentials;
    Users loginUser(LoginRequest newUser) throws InvalidCredentials;
    String bookTicket(Integer routeId, String email);


    Users updateUser(UserDto user) throws ResourceNotFoundException, InvalidCredentials;
}

