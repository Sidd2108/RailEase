package com.rail.railEase.service.Station;

import com.rail.railEase.dto.StationDto;
import com.rail.railEase.exception.EntryExistsException;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.model.Station;
import com.rail.railEase.repository.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rail.railEase.logger.LogClient.*;


import java.time.LocalDateTime;
import java.util.Optional;

import static com.rail.railEase.constant.Constants.SUCCESS;

@Service
public class StationServiceImpl implements StationService{

    @Autowired
    private StationRepo stationRepo;

    @Override
    public int createStation(Optional<StationDto> stationDtoOpt) throws EntryExistsException, ResourceNotFoundException {
        StationDto stationDto = stationDtoOpt
                .orElseThrow(() -> new ResourceNotFoundException("StationDto cannot be null"));

        if (stationRepo.existsById(stationDto.getStationId())) {
            throw new EntryExistsException("Station entry already exists");
        }

        Station newStation = new Station();
        newStation.setStationName(stationDto.getStationName());
        newStation.setStationId(stationDto.getStationId());
        newStation.setPrice(stationDto.getPrice());
        newStation.setCreatedAt(LocalDateTime.now());

        stationRepo.save(newStation);
        logger.info("(Admin) New station created with id : {} and name : {}", newStation.getStationId(), newStation.getStationName());

        return SUCCESS;

    }

    @Override
    public int updateStation(Optional<StationDto> stationDtoOpt) throws ResourceNotFoundException {
        StationDto stationDto = stationDtoOpt
                .orElseThrow(() -> new ResourceNotFoundException("StationDto cannot be null"));

        if (!stationRepo.existsById(stationDto.getStationId())) {
            throw new ResourceNotFoundException("Station with ID " + stationDto.getStationId() + " does not exist");

        }

        Station existingStation = stationRepo.findById(stationDto.getStationId()).get();

        existingStation.setStationName(stationDto.getStationName());
        existingStation.setPrice(stationDto.getPrice());
        existingStation.setCreatedAt(LocalDateTime.now());

        stationRepo.save(existingStation);
        logger.info("(Admin) Station updated with id : {} and name : {}", existingStation.getStationId(), existingStation.getStationName());
        return SUCCESS;
    }
    @Override
    public int deleteStation(Integer stationId) throws ResourceNotFoundException {
        if (!stationRepo.existsById(stationId)) {
            throw new ResourceNotFoundException("Station with ID " + stationId + " does not exist");
        }
        stationRepo.deleteById(stationId);
        logger.info("(Admin) Station deleted with id : {}", stationId);
        return SUCCESS;
    }
}
