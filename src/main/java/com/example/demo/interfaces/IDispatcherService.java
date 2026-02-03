package com.example.demo.interfaces;

import com.example.demo.dto.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDispatcherService {
    ResponseEntity<String> assignOfficerToUnit(Long oId,Long uId);
    ResponseEntity<String> changeUnitStatus(Long uId, Long sId);
    ResponseEntity<String> createIncident(CreateIncidentDto dto) ;
    ResponseEntity<String> assignUnitToIncident(Long uId, Long iId);
    ResponseEntity<String> sendRecord(Long uId, Long rId);
    ResponseEntity<List<GetAllIncidentsDto>> getAllIncidents();
    ResponseEntity<List<GetAllRecordsDto>> getAllRecords();
    ResponseEntity<List<GetAvailableOfficersDto>> getAvailableOfficers();
    ResponseEntity<List<GetUnitOfficersDto>> getUnitOfficers(Long uId);
    ResponseEntity<String> disengageOfficer(Long oId);
    ResponseEntity<List<GetIncidentAssignedUnitsDto>> getIncidentAssignedUnits(Long iId);
}
