package com.example.demo.controller;

import com.example.demo.dto.ResolveIncidentDto;
import com.example.demo.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("unit")
public class UnitController {
    private final UnitService unitService;

    @PostMapping("resolveIncident/{iId}")
    public ResponseEntity<String> resolveIncident(@PathVariable Long iId,@RequestBody ResolveIncidentDto dto){
        return unitService.resolveIncident(iId,dto);
    }
}
