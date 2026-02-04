package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Incident;
import com.example.demo.entity.Status;
import com.example.demo.entity.Unit;
import com.example.demo.entity.UnitRecord;
import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.UnitRecordRepository;
import com.example.demo.repository.UnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UnitService {
    private final IncidentRepository incidentRepository;
    private final UnitRepository unitRepository;
    private final UnitRecordRepository unitRecordRepository;
    private final StatusRepository statusRepository;

    public ResponseEntity<String> resolveIncident(Long iId,Long uId, ResolveIncidentDto dto) {
        //TODO change SecurityContextHolder everywhere
        Incident i = incidentRepository.findById(iId)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        i.setFinalReport(dto.getFinalReport());
        i.setVisible(false);

        Unit unit = unitRepository.findById(uId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        unit.setStatus(statusRepository.findByStatus("SAFE"));

        Status safeStatus = statusRepository.findByStatus("SAFE");
        if (safeStatus == null) {
            throw new RuntimeException("SAFE status not found in database");
        }

        unitRepository.save(unit);
        incidentRepository.save(i);
        return ResponseEntity.ok("Incident resolved");
    }


    public ResponseEntity<List<GetAllUnitsDto>> getAllUnits() {
        List<Unit> units = unitRepository.findAll();

        List<GetAllUnitsDto> dtoList = units.stream()
                .map(unit -> new GetAllUnitsDto(
                        unit.getId(),
                        unit.getCallSign(),
                        unit.getLat(),
                        unit.getLon(),
                        unit.getStatus().getStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);

    }


    public ResponseEntity<String> getUnitLocation(LatLonDto location) {
        Unit u = unitRepository.findById(location.getUId()).orElseThrow(() -> new RuntimeException("Not found"));
        u.setLat(location.getLat());
        u.setLon(location.getLon());
        unitRepository.save(u);
        return ResponseEntity.ok("Location updated");
    }

    public List<GetUnitRecordsDto> getUnitRecords(Long uId) {
        Unit unit = unitRepository.findById(uId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        // Fetch records associated with the unit
        List<UnitRecord> unitRecords = unitRecordRepository.findALlByUnitId(uId);
        return unitRecords.stream().map(record -> new GetUnitRecordsDto(
                record.getId(),
                record.getRecord().getFullName(),
                record.getRecord().getDate().toString(),
                record.getRecord().getAddress(),
                record.getRecord().getOffenseDetails()
                )).collect(Collectors.toList()
        );
    }

    public ResponseEntity<String> setSafe() {
        Unit unit = (Unit) SecurityContextHolder.getContext();
        unit.setStatus(statusRepository.findByStatus("SAFE"));
        unitRepository.save(unit);
        return ResponseEntity.ok("Unit set to SAFE");
    }
}
