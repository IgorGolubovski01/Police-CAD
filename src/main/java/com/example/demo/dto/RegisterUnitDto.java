package com.example.demo.dto;

import com.example.demo.entity.CriminalRecord;
import com.example.demo.entity.Officer;
import com.example.demo.entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RegisterUnitDto {

    // From User
    private String username;
    private String password;

    //From Unit
    private String callSign;
    private String licensePlate;

    private double lat;
    private double lon;




}
