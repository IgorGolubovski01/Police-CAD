package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.repository.UnitRepository;
import com.example.demo.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("unit")
public class UnitController {
    private final UnitService unitService;

    @PostMapping("resolveIncident/{iId}")
    public ResponseEntity<String> resolveIncident(@PathVariable Long iId,@RequestBody ResolveIncidentDto dto){
        return unitService.resolveIncident(iId,dto);
    }

    @GetMapping("getAllUnits")
    public ResponseEntity<List<GetAllUnitsDto>> getAllUnits(){
        return unitService.getAllUnits();
    }

    @PostMapping("getUnitLocation")
    public ResponseEntity<String> getUnitLocation(@RequestBody LatLonDto location) {
        return unitService.getUnitLocation(location);
    }

    @GetMapping("getUnitRecords/{uId}")
    public ResponseEntity<List<GetUnitRecordsDto>> getUnitRecords(@PathVariable Long uId){
        return ResponseEntity.ok(unitService.getUnitRecords(uId));
    }

    @PostMapping("setSafe")
    public ResponseEntity<String> setSafe(){
        return unitService.setSafe();
    }



}
