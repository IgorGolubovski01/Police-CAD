package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.interfaces.IDispatcherService;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class DispatcherService implements IDispatcherService {

    private final OfficerRepository officerRepository;
    private final UnitRepository unitRepository;
    private final StatusRepository statusRepository;
    private final IncidentTypeRepository incidentTypeRepository;
    private final IncidentRepository incidentRepository;
    private final DispatcherRepository dispatcherRepository;
    private final CriminalRecordRepository criminalRecordRepository;
    private final UnitRecordRepository unitRecordRepository;
    private final IncidentUnitRelRepository incidentUnitRelRepository;

    @Override
    public ResponseEntity<String> assignOfficerToUnit(Long oId, Long uId) {
        Officer officer = officerRepository.findById(oId).orElseThrow(() -> new RuntimeException("Officer not found"));
        Unit unit = unitRepository.findById(uId).orElseThrow(() -> new RuntimeException("Unit not found"));

        officer.setDeployed(true);
        unit.getOfficers().add(officer);

        officerRepository.save(officer);
        unitRepository.save(unit);
        return ResponseEntity.ok("Officer assigned to unit");
    }

    @Override
    public ResponseEntity<String> changeUnitStatus(Long uId, Long sId) {
        Unit unit = unitRepository.findById(uId).orElseThrow(() -> new RuntimeException("Unit not found"));
        Status status = statusRepository.findById(sId).orElseThrow(() -> new RuntimeException("Status not found"));

        switch (sId.intValue()) {
            case 1:
                unit.setStatus(status);
                unitRepository.save(unit);
                return ResponseEntity.ok("Unit status set to SAFE");
            case 2:
                unit.setStatus(status);
                unitRepository.save(unit);
                return ResponseEntity.ok("Unit status set to IN_ACTION");

            default:
                return ResponseEntity.badRequest().body("Bad Request");
        }
    }

    @Override
    public ResponseEntity<String> createIncident(CreateIncidentDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Dispatcher d = dispatcherRepository.findByUsername(username);
        IncidentType it = incidentTypeRepository.findByIncidentType(dto.getIncidentType());

        Incident i = new Incident();
        i.setDescription(dto.getDescription());
        i.setIncidentType(it);
        i.setIncidentTime(LocalDateTime.now());
        i.setAddress(dto.getAddress());
        i.setDispatcher(d);
        i.setFinalReport("N/A");
        double[] coordinates = calculateCoordinates(dto.getAddress());
        i.setLat(coordinates[0]);
        i.setLon(coordinates[1]);
        incidentRepository.save(i);
        return ResponseEntity.ok("Incident created successfully");
    }

    public double[] calculateCoordinates(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = "https://nominatim.openstreetmap.org/search?q="
                    + encodedAddress + "&format=json&limit=1";

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            if (root.isArray() && !root.isEmpty()) {
                JsonNode node = root.get(0);
                double lat = node.get("lat").asDouble();
                double lon = node.get("lon").asDouble();
                return new double[]{lat, lon};
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate coordinates", e);
        }

        throw new RuntimeException("Address not found");
    }

    @Override
    public ResponseEntity<String> assignUnitToIncident(Long uId, Long iId) {
        Unit unit = unitRepository.findById(uId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        Incident incident = incidentRepository.findById(iId)
                .orElseThrow(() -> new RuntimeException("Incident not found"));

        IncidentUnitRel rel = new IncidentUnitRel();
        rel.setIncident(incident);
        rel.setUnit(unit);

        unit.setStatus(statusRepository.findByStatus("IN_ACTION"));

        incidentUnitRelRepository.save(rel);
        unitRepository.save(unit);
        return ResponseEntity.ok("Unit assigned to incident");
    }

    @Override
    public ResponseEntity<String> sendRecord(Long uId, Long rId) {
        Unit unit = unitRepository.findById(uId)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + uId));
        CriminalRecord record = criminalRecordRepository.findById(rId)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + rId));

        UnitRecord unitRecord = new UnitRecord();
        unitRecord.setUnit(unit);
        unitRecord.setRecord(record);
        unitRecord.setAddedAt(LocalDateTime.now());

        unitRecordRepository.save(unitRecord);

        return ResponseEntity.ok("Record sent successfully!");

    }


    public ResponseEntity<List<GetAllIncidentsDto>> getAllIncidents() {
        // Retrieve all incidents from the repository
        List<Incident> incidents = incidentRepository.findByVisibleTrue(true);

        // Map each incident entity to GetAllIncidentsDto
        List<GetAllIncidentsDto> dtoList = incidents.stream()
                .map(incident -> {
                    GetAllIncidentsDto dto = new GetAllIncidentsDto();
                    dto.setId(incident.getId());
                    dto.setDescription(incident.getDescription());
                    dto.setIncidentType(incident.getIncidentType().getIncidentType());
                    dto.setIncidentTime(incident.getIncidentTime().toString());
                    dto.setAddress(incident.getAddress());
                    dto.setLat(String.valueOf(incident.getLat()));
                    dto.setLon(String.valueOf(incident.getLon()));
                    dto.setDispatcher(incident.getDispatcher().getUsername());
                    dto.setFinalReport(incident.getFinalReport());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<List<GetAllRecordsDto>> getAllRecords() {
        List<CriminalRecord> records = criminalRecordRepository.findAll();

        List<GetAllRecordsDto> recordDtos = records.stream().map(record -> {
            GetAllRecordsDto dto = new GetAllRecordsDto(

                    record.getId(),
                    record.getFullName(),
                    record.getDate().toString(),
                    record.getAddress());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(recordDtos);

    }

    @Override
    public ResponseEntity<List<GetAvailableOfficersDto>> getAvailableOfficers() {
        List<Officer> officers = officerRepository.findByDeployed(false);

        List<GetAvailableOfficersDto> officerDtos = officers.stream().map( officer -> {
            GetAvailableOfficersDto dto = new GetAvailableOfficersDto(
                    officer.getId(),
                    officer.getFName(),
                    officer.getLName(),
                    officer.getBadgeNumber());
            return dto;
        }).toList();

        return ResponseEntity.ok(officerDtos);
    }

    @Override
    public ResponseEntity<List<GetUnitOfficersDto>> getUnitOfficers(Long uId) {
        Unit unit = unitRepository.findById(uId).orElseThrow(() -> new RuntimeException("Unit not found"));

        Set<GetUnitOfficersDto> unitOfficersDtos = unit.getOfficers().stream().map( officer -> {
            GetUnitOfficersDto dto = new GetUnitOfficersDto(
                    officer.getId(),
                    officer.getFName(),
                    officer.getLName(),
                    officer.getBadgeNumber());
            return dto;
        }).collect(Collectors.toSet());

        return ResponseEntity.ok(List.copyOf(unitOfficersDtos));
    }

    @Override
    public ResponseEntity<String> disengageOfficer(Long oId) {
        Officer officer = officerRepository.findById(oId)
                .orElseThrow(() -> new RuntimeException("Officer with ID " + oId + " not found."));

        Unit assignedUnit = unitRepository.findAll().stream()
                .filter(unit -> unit.getOfficers().contains(officer))
                .findFirst()
                .orElse(null);

        if (assignedUnit == null) {
            return ResponseEntity.status(404).body("Officer is not assigned to any unit.");
        }

        assignedUnit.getOfficers().remove(officer);
        officer.setDeployed(false);
        officerRepository.save(officer);
        unitRepository.save(assignedUnit);
        return ResponseEntity.ok("Officer with ID " + oId + " successfully disengaged from unit " + assignedUnit.getCallSign() + ".");
    }

    @Override
    public ResponseEntity<List<GetIncidentAssignedUnitsDto>> getIncidentAssignedUnits(Long iId) {
        List<IncidentUnitRel> relations = incidentUnitRelRepository.findByIncidentId(iId);
        List<GetIncidentAssignedUnitsDto> dtos = relations.stream()
                .map(rel -> new GetIncidentAssignedUnitsDto(
                        rel.getUnit().getId(),
                        rel.getUnit().getCallSign(),
                        rel.getUnit().getLicensePlate(),
                        rel.getUnit().getStatus().getStatus()
                ))
                .toList();

        return ResponseEntity.ok(dtos);

    }

    @Override
    public ResponseEntity<List<GetIncidentUnitRelDto>> getAllIncidentUnitRels() {
        List<IncidentUnitRel> relations = incidentUnitRelRepository.findAll();

        List<GetIncidentUnitRelDto> dtos = relations.stream()
                .map(rel -> new GetIncidentUnitRelDto(
                        rel.getUnit().getId(),
                        rel.getIncident().getId(),
                        rel.isActive()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }


}
