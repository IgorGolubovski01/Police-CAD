package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request){
        return userService.login(request);
    }
}
