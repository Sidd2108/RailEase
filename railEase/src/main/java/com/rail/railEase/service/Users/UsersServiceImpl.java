package com.rail.railEase.service.Users;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rail.railEase.dto.LoginRequest;
import com.rail.railEase.dto.UserDto;
import com.rail.railEase.exception.InvalidCredentials;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.exception.UserAlreadyExists;
import static com.rail.railEase.logger.LogClient.logger;
import com.rail.railEase.model.Route;
import com.rail.railEase.model.Users;
import com.rail.railEase.repository.RouteRepo;
import com.rail.railEase.repository.UsersRepo;
import com.rail.railEase.service.Ticket.TicketService;


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


    private StringBuilder checkCredentials(String username, String email, String password, String phone) {
        StringBuilder errormsg = new StringBuilder();
       
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
    public UserDto loginUser(LoginRequest newuser) throws InvalidCredentials {
        Optional<Users> useropt = userRepo.findByEmail(newuser.getEmail());
        if (useropt.isPresent()) {
            Users user = useropt.get();
            if (passwordEncoder.matches(newuser.getPassword(), user.getPassword())) {
                logger.info("Login successful for user id : {} and name : {}", user.getUserId(), user.getUsername());
                return new UserDto(user.getUserId(), user.getUsername(),user.getEmail(),user.getPhone(),user.getBalance());
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
    public UserDto updateUser(Users user) throws ResourceNotFoundException, InvalidCredentials {

        Users newUser = userRepo.findByEmail(user.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("user not found"));

        StringBuilder errormsg = checkCredentials( user.getUsername(),user.getEmail(), user.getPassword(),user.getPhone());

        if(errormsg.length() == 0) {
            newUser.setPassword(passwordEncoder.encode(user.getPassword())); // Ensure password is hashed
            newUser.setUsername(user.getUsername());
            newUser.setPhone(user.getPhone());
            newUser.setEmail(user.getEmail());
            newUser.setBalance(user.getBalance());
            newUser.setCreatedAt(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            userRepo.save(newUser);
            logger.info("User updated with id : {} and name : {}", newUser.getUserId(), newUser.getUsername());

            return new UserDto(user.getUserId(), user.getUsername(),user.getEmail(),user.getPhone(),user.getBalance());

        }
        else throw new InvalidCredentials(errormsg.toString());
    }
}
