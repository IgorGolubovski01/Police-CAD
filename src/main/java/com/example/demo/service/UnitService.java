package com.example.demo.service;

import com.example.demo.dto.ResolveIncidentDto;
import com.example.demo.entity.Incident;
import com.example.demo.repository.IncidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UnitService {
    private final IncidentRepository incidentRepository;

    public ResponseEntity<String> resolveIncident(Long iId, ResolveIncidentDto dto) {
        Incident i = incidentRepository.findById(iId).orElseThrow(() -> new RuntimeException("Not found"));
        i.setFinalReport(dto.getFinalReport());
        incidentRepository.save(i);
        return ResponseEntity.ok("Incident resolved");
    }
}
