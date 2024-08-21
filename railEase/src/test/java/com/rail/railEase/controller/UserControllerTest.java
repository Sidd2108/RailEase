package com.rail.railEase.controller;


import com.rail.railEase.exception.InvalidCredentials;
import com.rail.railEase.exception.UserAlreadyExists;
import com.rail.railEase.model.Users;
import com.rail.railEase.service.Users.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserControllerTest.class})
public class UserControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    private Users user;

    @BeforeEach
    void setUp(){
        user = new Users(1,"Riya","riya123","riya@gmailcom","9920993837", LocalDateTime.now(), 100L);
    }

    @Test
    void testRegisterUser_Success() throws UserAlreadyExists, InvalidCredentials {


        when(usersService.registerUser(user)).thenReturn(user);
        ResponseEntity<Object> response = usersController.registerUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testRegisterUser_UserAlreadyExists() throws UserAlreadyExists, InvalidCredentials {
        String expectedMessage = "User already exists";

        when(usersService.registerUser(user))
                .thenThrow(new UserAlreadyExists(expectedMessage));

        ResponseEntity<Object> response = usersController.registerUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

}
