package com.rail.railEase.repository;

import com.rail.railEase.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StationRepo extends JpaRepository<Station, Integer> {
    Optional<Station> findByStationName(String name);

    @Query(value = "SELECT s.price FROM Station s WHERE s.stationName =:name")
    double findPriceByStationName(@Param("name") String stationName);
}
