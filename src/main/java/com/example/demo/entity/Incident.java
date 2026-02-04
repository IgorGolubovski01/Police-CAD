package com.example.demo.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    private IncidentType incidentType;
    private LocalDateTime incidentTime;
    private String address;
    private double lat;
    private double lon;
    @ManyToOne
    private Dispatcher dispatcher;
    private String finalReport;
    private boolean visible = true;

}
