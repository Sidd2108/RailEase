package com.rail.railEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto {
    private String routeId;
    private String departureStation;
    private String arrivalStation;
    private Double amount;
    private String paymentTime;
    private String paymentStatus;
    private String issuedTo;

    // Constructors, getters, and setters
}