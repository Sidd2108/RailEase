package com.rail.railEase.controller;

import com.rail.railEase.dto.StationDto;
import com.rail.railEase.exception.EntryExistsException;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.service.Station.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping("/createStation")
    public ResponseEntity<Object> createStation(@RequestBody StationDto station) {
        try {
            int result = stationService.createStation(Optional.ofNullable(station));
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (EntryExistsException | ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateStaion")
    public ResponseEntity<Object> updateStation(@RequestBody StationDto station) {
        try{
            int result = stationService.updateStation(Optional.ofNullable(station));
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteStation")
    public ResponseEntity<Object> deleteStation(@RequestParam Integer stationID) {
        try{
            int result = stationService.deleteStation(stationID);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}