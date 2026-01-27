package com.example.demo.interfaces;

import com.example.demo.dto.CreateIncidentDto;
import org.springframework.http.ResponseEntity;

public interface IDispatcherService {
    ResponseEntity<String> assignOfficerToUnit(Long oId,Long uId);
    ResponseEntity<String> changeUnitStatus(Long uId, Long sId);
    ResponseEntity<String> createIncident(CreateIncidentDto dto) ;
    ResponseEntity<String> assignUnitToIncident(Long uId, Long iId);
    ResponseEntity<String> sendRecord(Long uId, Long rId);
}
