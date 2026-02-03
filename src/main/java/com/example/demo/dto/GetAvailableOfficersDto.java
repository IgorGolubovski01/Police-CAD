package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAvailableOfficersDto {
    private Long id;
    private String fName;
    private String lName;
    private int badgeNumber;
}
