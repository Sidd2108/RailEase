package com.rail.railEase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="route_id")
    private Integer routeId;

    @ManyToOne
    @JoinColumn(name ="departure_station_id")
    private Station departureStation;
    @ManyToOne
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private double fare;
}
