package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetIncidentAssignedUnitsDto {
    private Long id;               // Unit ID
    private String callSign;       // Unit call sign
    private String licensePlate;   // Unit license plate
    private String statusName;     // Unit's current status name

}
