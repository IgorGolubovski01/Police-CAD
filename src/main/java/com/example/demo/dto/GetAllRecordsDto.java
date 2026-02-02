package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class GetAllRecordsDto {
    private Long id;
    private String fullName;
    private String dateTime;
    private String address;
}
