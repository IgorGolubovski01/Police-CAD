package com.example.demo.controller;

import com.example.demo.dto.CreateIncidentDto;
import com.example.demo.dto.GetAllUnitsDto;
import com.example.demo.service.DispatcherService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<String> sendUnitToRecord(@PathVariable Long uId, @PathVariable Long rId){
        return dispatcherService.sendRecord(uId,rId);
    }

    @GetMapping("getAllUnits")
    public ResponseEntity<List<GetAllIncidentsDto>> getAllUnits(){
        return unitService.getAllUnits();
    }




}
