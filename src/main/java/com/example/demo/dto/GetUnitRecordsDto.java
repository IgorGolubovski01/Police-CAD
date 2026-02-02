package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUnitRecordsDto {
    private Long id;
    private String fullName;
    private String dateTime;
    private String address;
    private String offenseDetails;
}
