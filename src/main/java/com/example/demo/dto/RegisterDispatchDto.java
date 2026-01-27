package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterDispatchDto {
    private String username;
    private String password;

    private String fName;
    private String lName;
}
