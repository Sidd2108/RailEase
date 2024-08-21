package com.rail.railEase.service.Route;

import com.rail.railEase.dto.FareDto;
import com.rail.railEase.dto.RouteDto;
import com.rail.railEase.dto.RouteResponseDto;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.model.Route;

import java.util.Optional;

public interface RouteService {
    RouteResponseDto createRoute(Optional<RouteDto> routeDtoOpt) throws ResourceNotFoundException;

}
