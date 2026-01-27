package com.example.demo.controller;

import com.example.demo.dto.RegisterAdminDto;
import com.example.demo.dto.RegisterDispatchDto;
import com.example.demo.dto.RegisterUnitDto;
import com.example.demo.entity.Dispatcher;
import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterAdminDto dto){
        return adminService.registerAdmin(dto);
    }

    @PostMapping("registerDispatcher")
    public ResponseEntity<String> registerDispatcher(@RequestBody RegisterDispatchDto dto){
        return adminService.registerDispatcher(dto);
    }

    @PostMapping("registerUnit")
    public ResponseEntity<String> registerUnit(@RequestBody RegisterUnitDto dto){
        return adminService.registerUnit(dto);
    }



}
