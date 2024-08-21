package com.rail.railEase.service.Users;

import com.rail.railEase.dto.LoginRequest;
import com.rail.railEase.dto.UserDto;
import com.rail.railEase.exception.*;
import com.rail.railEase.model.Route;
import com.rail.railEase.model.Users;
import com.rail.railEase.repository.RouteRepo;
import com.rail.railEase.repository.UsersRepo;
import com.rail.railEase.service.Ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.rail.railEase.logger.LogClient.*;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.rail.railEase.constant.Constants.SUCCESS;


@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepo userRepo;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RouteRepo routeRepo;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    private StringBuilder checkCredentials(String username, String email, String password, String phone) {
        StringBuilder errormsg = new StringBuilder();
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            errormsg.append("Invalid email format.\n");
        }
        if (email.length() > 25) {
            errormsg.append("Email length should be less than or equal to 25.\n");
        }

        if (username.length() > 10) {
            errormsg.append("Username length should be less than or equal to 10.\n");
        }

        if (password.length() > 20) {
            errormsg.append("Password length should be less than or equal to 20.\n");
        }

        if (phone.length() > 20) {
            errormsg.append("Phone number length should be less than or equal to 20 digits.\n");
        }
        return errormsg;
    }


    @Override
    public Users registerUser(Users user) throws UserAlreadyExists,InvalidCredentials {

        Optional<Users> existingUserOpt = userRepo.registerCheck(user.getEmail(),user.getPhone(), user.getPassword());
        if (existingUserOpt.isPresent()) {
            throw new UserAlreadyExists("User already exists. Please Login.");

        }
        StringBuilder errormsg = checkCredentials( user.getUsername(),user.getEmail(), user.getPassword(),user.getPhone());

        if(errormsg.length() == 0) {

            Users newUser = new Users();
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setUsername(user.getUsername());
            newUser.setPhone(user.getPhone());
            newUser.setEmail(user.getEmail());
            newUser.setBalance(user.getBalance());
            newUser.setCreatedAt(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            userRepo.save(newUser);
            logger.info("New user registered with id : {} and name : {}", newUser.getUserId(), newUser.getUsername());

            return newUser;
        }
        else {
            throw new InvalidCredentials(errormsg.toString());
        }
    }

    @Override
    public Users loginUser(LoginRequest newuser) throws InvalidCredentials {
        Optional<Users> useropt = userRepo.findByEmail(newuser.getEmail());
        if (useropt.isPresent()) {
            Users user = useropt.get();
            if (passwordEncoder.matches(newuser.getPassword(), user.getPassword())) {
                logger.info("Login successful for user id : {} and name : {}", user.getUserId(), user.getUsername());
                return user;
            } else {
                logger.error("Authentication failed for user id : {}. Error : Incorrect password", user.getUserId());
                throw new InvalidCredentials("Incorrect Password. Cannot login.");
            }
        } else {
            throw new InvalidCredentials("Invalid Credentials. Please enter correct email and password.");
        }
    }

    @Override
    public String bookTicket(Integer routeId, String email) throws ResourceNotFoundException {
        Route route = routeRepo.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with ID " + routeId));

        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        return ticketService.createTicket(Optional.ofNullable(route), Optional.ofNullable(user));
    }

    @Override
    public Users updateUser(UserDto user) throws ResourceNotFoundException, InvalidCredentials {

        Users newUser = userRepo.findById(user.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("user not found"));

        StringBuilder errormsg = checkCredentials( user.getUsername(),user.getEmail(), user.getPassword(),user.getPhone());

        if(errormsg.length() == 0) {
            newUser.setPassword(user.getPassword()); // Ensure password is hashed
            newUser.setUsername(user.getUsername());
            newUser.setPhone(user.getPhone());
            newUser.setEmail(user.getEmail());
            newUser.setBalance(user.getBalance());
            newUser.setCreatedAt(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            userRepo.save(newUser);
            logger.info("User updated with id : {} and name : {}", newUser.getUserId(), newUser.getUsername());

            return newUser;
        }
        else throw new InvalidCredentials(errormsg.toString());
    }
}
