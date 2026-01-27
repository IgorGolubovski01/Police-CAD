package com.example.demo.interfaces;

import com.example.demo.dto.RegisterAdminDto;
import com.example.demo.dto.RegisterDispatchDto;
import com.example.demo.dto.RegisterUnitDto;
import org.springframework.http.ResponseEntity;

public interface IAdminService {
    ResponseEntity<String> registerAdmin(RegisterAdminDto dto);
    ResponseEntity<String> registerDispatcher(RegisterDispatchDto dto);
    ResponseEntity<String> registerUnit(RegisterUnitDto dto);

}
