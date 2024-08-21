package com.rail.railEase.controller;

import com.rail.railEase.dto.FareDto;
import com.rail.railEase.dto.RouteDto;
import com.rail.railEase.dto.RouteResponseDto;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.model.Route;
import com.rail.railEase.service.Route.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/createRouteAndgetFare")
    public ResponseEntity<Object> getFare(@RequestBody RouteDto routeDto){
        try{
            RouteResponseDto fare = routeService.createRoute(Optional.ofNullable(routeDto));
            return new ResponseEntity<>(fare, HttpStatus.OK);
        } catch ( RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
