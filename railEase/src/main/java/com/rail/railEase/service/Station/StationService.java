package com.rail.railEase.service.Station;

import com.rail.railEase.dto.StationDto;
import com.rail.railEase.exception.EntryExistsException;
import com.rail.railEase.exception.ResourceNotFoundException;

import java.util.Optional;

public interface StationService {
    int createStation(Optional<StationDto> station) throws EntryExistsException;

    int updateStation(Optional<StationDto> stationDto);


    int deleteStation(Integer stationId) throws ResourceNotFoundException;
}
