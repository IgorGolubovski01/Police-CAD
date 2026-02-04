package com.example.demo.dto;

import com.example.demo.entity.Unit;
import lombok.Data;

import java.util.Set;

@Data
public class GetAllIncidentsDto {
    private Long id;
    private String description;
    private String incidentType;
    private String incidentTime;
    private String address;
    private String lat;
    private String lon;
    private String dispatcher;
    private String finalReport;
}
