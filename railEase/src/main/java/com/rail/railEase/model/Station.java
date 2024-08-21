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
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="station_id")
    private Integer stationId;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name ="line_1")
    private char line1;

    @Column(name="line_2")
    private char line2;

    private Double price;


//    @OneToMany(mappedBy = "departureStation")
//    private List<Route> departureRoutes;
//
//    @OneToMany(mappedBy = "arrivalStation")
//    private List<Route> arrivalRoutes;

}
