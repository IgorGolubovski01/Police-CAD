package com.example.demo.service;

import com.example.demo.dto.CreateIncidentDto;
import com.example.demo.dto.GetAllIncidentsDto;
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

    @Override
    public ResponseEntity<String> assignOfficerToUnit(Long oId, Long uId) {
        Officer officer = officerRepository.findById(oId).get();
        Unit unit = unitRepository.findById(uId).get();
        unit.getOfficers().add(officer);

        unitRepository.save(unit);
        return ResponseEntity.ok("Officer assigned to unit");
    }

    @Override
    public ResponseEntity<String> changeUnitStatus(Long uId, Long sId) {
        Unit unit = unitRepository.findById(uId).get();
        Status status = statusRepository.findById(sId).get();

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
        double[] coordinates = calculateCoordinates(dto.getAddress());
        i.setLat(coordinates[0]);
        i.setLon(coordinates[1]);
        incidentRepository.save(i);
        return null;
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
        Unit unit = unitRepository.findById(uId).get();
        Incident incident = incidentRepository.findById(iId).get();
        incident.getUnitsRespond().add(unit);
        incidentRepository.save(incident);
        return ResponseEntity.ok("Unit assigned to incident");
    }

    @Override
    public ResponseEntity<String> sendRecord(Long uId, Long rId) {
        Unit unit = unitRepository.findById(uId).get();
        CriminalRecord r = criminalRecordRepository.findById(rId).get();
        unit.getRecords().add(r);
        unitRepository.save(unit);

        return ResponseEntity.ok("Record sent");
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
                    dto.setUnits(incident.getUnitsRespond());
                    dto.setFinalReport(incident.getFinalReport());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
}
