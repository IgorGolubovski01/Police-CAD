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

    @PostMapping("resolveIncident/{iId}/{uId}")
    public ResponseEntity<String> resolveIncident(@PathVariable Long iId,@PathVariable Long uId,@RequestBody ResolveIncidentDto dto){
        return unitService.resolveIncident(iId, uId, dto);
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

    @PostMapping("setSafe/{uId}")
    public ResponseEntity<String> setSafe(@PathVariable Long uId){
        return unitService.setSafe(uId);
    }



}
