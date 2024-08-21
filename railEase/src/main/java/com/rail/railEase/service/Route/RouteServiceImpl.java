// src/main/java/com/rail/railEase/service/Route/RouteServiceImpl.java
package com.rail.railEase.service.Route;

import com.rail.railEase.dto.FareDto;
import com.rail.railEase.dto.RouteDto;
import com.rail.railEase.dto.RouteResponseDto;
import com.rail.railEase.exception.ResourceNotFoundException;
import com.rail.railEase.model.Route;
import com.rail.railEase.model.Station;
import com.rail.railEase.repository.RouteRepo;
import com.rail.railEase.repository.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.rail.railEase.logger.LogClient.logger;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepo routeRepo;

    @Autowired
    private StationRepo stationRepo;

    // creates route
    @Override
    public RouteResponseDto createRoute(Optional<RouteDto> routeDtoOpt) throws ResourceNotFoundException {

        RouteDto routeDto = routeDtoOpt.orElseThrow(() -> new ResourceNotFoundException("Route cannot be null"));
        Station arrivalStation = stationRepo.findByStationName(routeDto.getArrivalStation())
                .orElseThrow(() -> new ResourceNotFoundException("Arrival station with name " + routeDto.getArrivalStation() + " not found"));

        Station departureStation = stationRepo.findByStationName(routeDto.getDepartureStation())
                .orElseThrow(() -> new ResourceNotFoundException("Departure station with name " + routeDto.getDepartureStation() + " not found"));

        Optional<Route> existsFare = checkRouteExists(arrivalStation,departureStation);
        if(existsFare.isPresent()){
            return new RouteResponseDto(existsFare.get().getRouteId(),existsFare.get().getFare());
        }


        // Create and save route
        Route route = new Route();
        route.setArrivalStation(arrivalStation);
        route.setDepartureStation(departureStation);
        route.setCreatedAt(LocalDateTime.now());
        routeRepo.save(route);
        logger.info("New route created with id : {} ", route.getRouteId());

        // Calculate fare
        double cost = calculateFare(departureStation, arrivalStation);

        // Set fare and save the route again
        route.setFare(cost);
        routeRepo.save(route);



        // Return RouteResponseDto with routeId and fare
        return new RouteResponseDto(route.getRouteId(), cost);
    }

    private double calculateFare(Station sourceStation, Station destStation) {
        double cost = 0;
        double kurlaPrice = stationRepo.findPriceByStationName("Kurla");
        double dadarPrice = stationRepo.findPriceByStationName("Dadar");

        // Logic to calculate fare
        while (true) {
            if (sourceStation.getLine1() == destStation.getLine1()
                    || sourceStation.getLine1() == destStation.getLine2()
                    || sourceStation.getLine2() == destStation.getLine1()
                    || sourceStation.getLine2() == destStation.getLine2()) {
                cost += Math.abs(sourceStation.getPrice() - destStation.getPrice());
                break;
            } else if (sourceStation.getLine1() == 'W' && sourceStation.getLine2() == 'W') {
                cost += Math.abs(sourceStation.getPrice() - dadarPrice);
                sourceStation = stationRepo.findById(9).get();
            } else if (sourceStation.getStationName().equals("Dadar")) {
                cost += Math.abs(sourceStation.getPrice() - kurlaPrice);
                sourceStation = stationRepo.findById(69).get();
            } else if (sourceStation.getLine1() == 'H' && sourceStation.getLine2() == 'H') {
                cost += Math.abs(sourceStation.getPrice() - kurlaPrice);
                sourceStation = stationRepo.findById(69).get();
            } else {
                cost += Math.abs(sourceStation.getPrice() - dadarPrice);
                sourceStation = stationRepo.findById(9).get();
            }
        }

        return cost == 0 ? 5 : cost; // Ensure cost is at least 5
    }
    public Optional<Route> checkRouteExists(Station arrival, Station departure) {
        Optional<Route> route = routeRepo.findRouteByStations(departure, arrival);
        if (route.isPresent()) {
            return route;
        }
        return Optional.empty();
    }
}
