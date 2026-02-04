package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetIncidentUnitRelDto {
    private Long unitId;
    private Long incidentId;
    private boolean active;
}
