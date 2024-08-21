package com.rail.railEase.dto;


import com.rail.railEase.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Integer id;
    private Integer user_id;
    private Integer route_id;
    private String departureStation;
    private String arrivalStation;
    private LocalDateTime createdAt;

}
