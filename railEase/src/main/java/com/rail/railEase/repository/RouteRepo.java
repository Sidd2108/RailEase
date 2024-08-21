package com.rail.railEase.repository;

import com.rail.railEase.model.Route;
import com.rail.railEase.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RouteRepo extends JpaRepository<Route, Integer> {
    @Query("SELECT a FROM Route a WHERE a.departureStation = :d AND a.arrivalStation = :arrival")
    Optional<Route> findRouteByStations(@Param("d") Station departureStation, @Param("arrival") Station arrivalStation);


}
