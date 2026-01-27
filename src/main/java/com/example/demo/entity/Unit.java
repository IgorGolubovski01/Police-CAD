package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Unit extends User{

    private String callSign;
    private String licensePlate;
    @OneToMany
    private Set<Officer> officers = new HashSet<>();

    private double lat;
    private double lon;

    @ManyToOne
    private Status status;

    @OneToMany
    private List<CriminalRecord> records = new ArrayList<>();

}
