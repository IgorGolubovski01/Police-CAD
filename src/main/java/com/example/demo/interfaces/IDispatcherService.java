package com.example.demo.interfaces;

import com.example.demo.dto.CreateIncidentDto;
import com.example.demo.dto.GetAllIncidentsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDispatcherService {
    ResponseEntity<String> assignOfficerToUnit(Long oId,Long uId);
    ResponseEntity<String> changeUnitStatus(Long uId, Long sId);
    ResponseEntity<String> createIncident(CreateIncidentDto dto) ;
    ResponseEntity<String> assignUnitToIncident(Long uId, Long iId);
    ResponseEntity<String> sendRecord(Long uId, Long rId);
    ResponseEntity<List<GetAllIncidentsDto>> getAllIncidents();
}
