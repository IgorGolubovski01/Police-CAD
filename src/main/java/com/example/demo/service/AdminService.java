package com.example.demo.service;

import com.example.demo.dto.RegisterAdminDto;
import com.example.demo.dto.RegisterDispatchDto;
import com.example.demo.dto.RegisterUnitDto;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Dispatcher;
import com.example.demo.entity.Status;
import com.example.demo.entity.Unit;
import com.example.demo.interfaces.IAdminService;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService implements IAdminService {

    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final DispatcherRepository dispatcherRepository;
    private final PasswordEncoder encoder;
    private final UnitRepository unitRepository;
    private final StatusRepository status;

    @Override
    public ResponseEntity<String> registerAdmin(RegisterAdminDto dto) {
        Admin a = new Admin();

        a.setRole(roleRepository.findByRoleName("ROLE_ADMIN"));
        a.setUsername(dto.getUsername());
        a.setPassword(encoder.encode(dto.getPassword()));
        a.setFName(dto.getFName());
        a.setLName(dto.getLName());

        adminRepository.save(a);

        return new ResponseEntity<>("Admin Registered",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> registerDispatcher(RegisterDispatchDto dto) {
        Dispatcher d = new Dispatcher();

        d.setRole(roleRepository.findByRoleName("ROLE_DISPATCHER"));
        d.setUsername(dto.getUsername());
        d.setPassword(encoder.encode(dto.getPassword()));
        d.setFName(dto.getFName());
        d.setLName(dto.getLName());

        dispatcherRepository.save(d);

        return new ResponseEntity<>("Dispatcher Registered",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> registerUnit(RegisterUnitDto dto) {
        Unit u = new Unit();
        u.setRole(roleRepository.findByRoleName("ROLE_UNIT"));
        u.setUsername(dto.getUsername());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setCallSign(dto.getCallSign());
        u.setLicensePlate(dto.getLicensePlate());
        u.setLat(0);
        u.setLon(0);
        u.setStatus(status.findByStatus("SAFE"));

        unitRepository.save(u);

        return new ResponseEntity<>("Unit Registered",HttpStatus.OK);
    }


}
