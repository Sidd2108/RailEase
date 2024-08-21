package com.rail.railEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {

    private Integer stationId;
    private String stationName;

    private Double price;


}