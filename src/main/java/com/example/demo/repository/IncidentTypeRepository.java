package com.example.demo.repository;

import com.example.demo.entity.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentTypeRepository extends JpaRepository<IncidentType, Long> {
    IncidentType findByIncidentType(String incidentType);
}
