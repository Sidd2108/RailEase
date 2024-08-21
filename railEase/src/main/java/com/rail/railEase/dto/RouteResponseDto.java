package com.rail.railEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RouteResponseDto {

    private int routeId;
    private double fare;

    // Constructor
    public RouteResponseDto(int routeId, double fare) {
        this.routeId = routeId;
        this.fare = fare;
    }

    // Getters and setters
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}
