package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllUnitsDto {
    private Long id;
    private String callSign;
    private double lat;
    private double lon;
    private String status;
}
