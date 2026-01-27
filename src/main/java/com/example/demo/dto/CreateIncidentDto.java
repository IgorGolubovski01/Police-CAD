package com.example.demo.dto;

import lombok.Data;



@Data
public class CreateIncidentDto {

    private String description;
    private String incidentType;
    private String address;

}
