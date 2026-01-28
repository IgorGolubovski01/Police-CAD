package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Dispatcher;
import com.example.demo.entity.Unit;
import com.example.demo.entity.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.DispatcherRepository;
import com.example.demo.repository.UnitRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final AuthenticationManager authMng;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final UnitRepository unitRepository;
    private final DispatcherRepository dispatcherRepository;

    public User findByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return admin;
        }

        Unit unit = unitRepository.findByUsername(username);
        if (unit != null) {
            return unit;
        }

        Dispatcher dispatcher = dispatcherRepository.findByUsername(username);
        if (dispatcher != null) {
            return dispatcher;
        }

        throw new UsernameNotFoundException("User not found ");
    }


    public ResponseEntity<UserDto> login(LoginRequest request) {
        Authentication auth = authMng.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (auth.isAuthenticated()){
            User user = this.findByUsername(request.getUsername());

            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());

            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
