package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class IncidentUnitRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Incident incident;
    @ManyToOne
    private Unit unit;
}
