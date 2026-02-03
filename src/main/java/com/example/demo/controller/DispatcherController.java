package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.DispatcherService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("dispatcher")
public class DispatcherController{
    private final DispatcherService dispatcherService;

    @PostMapping("/unit/{uId}/officer/{oId}")
    public ResponseEntity<String> assignOfficerToUnit(@PathVariable Long oId, @PathVariable Long uId){
        return dispatcherService.assignOfficerToUnit(oId,uId);
    }

    @PostMapping("/unit/{uId}/status/{sId}")
    public ResponseEntity<String> changeUnitStatus(@PathVariable Long uId, @PathVariable Long sId){
        return dispatcherService.changeUnitStatus(uId,sId);
    }

    @PostMapping("createIncident")
    public ResponseEntity<String> createIncident(@RequestBody CreateIncidentDto dto){
        return dispatcherService.createIncident(dto);
    }

    @PostMapping("/unit/{uId}/incident/{iId}")
    public ResponseEntity<String> assignUnitToIncident(@PathVariable Long uId, @PathVariable Long iId){
        return dispatcherService.assignUnitToIncident(uId,iId);
    }

    @PostMapping("/unit/{uId}/record/{rId}")
    public ResponseEntity<String> sendRecordToUnit(@PathVariable Long uId, @PathVariable Long rId){
        return dispatcherService.sendRecord(uId,rId);
    }

    @GetMapping("getAllIncidents")
    public ResponseEntity<List<GetAllIncidentsDto>> getAllIncidents(){
        return dispatcherService.getAllIncidents();
    }

    @GetMapping("getAllRecords")
    public ResponseEntity<List<GetAllRecordsDto>> getAllRecords(){
        return dispatcherService.getAllRecords();
    }

    @GetMapping("getAvailableOfficers")
    public ResponseEntity<List<GetAvailableOfficersDto>> getAvailableOfficers(){
        return dispatcherService.getAvailableOfficers();
    }

    @GetMapping("getUnitOfficers/{uId}")
    public ResponseEntity<List<GetUnitOfficersDto>> getUnitOfficers(@PathVariable Long uId){
        return dispatcherService.getUnitOfficers(uId);
    }

    @DeleteMapping("disengageOfficer/{oId}")
    public ResponseEntity<String> disengageOfficer(@PathVariable Long oId){
        return dispatcherService.disengageOfficer(oId);
    }

    @GetMapping("getIncidentAssignedUnits/incident/{iId}")
    public ResponseEntity<List<GetIncidentAssignedUnitsDto>> getIncidentAssignedUnits(@PathVariable Long iId){
        return dispatcherService.getIncidentAssignedUnits(iId);
    }





}
