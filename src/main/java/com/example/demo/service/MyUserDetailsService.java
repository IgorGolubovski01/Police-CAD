package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.DispatcherRepository;
import com.example.demo.repository.UnitRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final UnitRepository unitRepository;
    private final DispatcherRepository dispatcherRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return new UserPrincipal(admin);
        }
        Unit unit = unitRepository.findByUsername(username);
        if (unit != null) {
            return new UserPrincipal(unit);
        }
        Dispatcher dispatcher = dispatcherRepository.findByUsername(username);
        if(dispatcher != null){
            return new UserPrincipal(dispatcher);
        }



        throw new UsernameNotFoundException(username);

//        System.out.println("Looking for user: " + username);
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            System.out.println("User not found!");
//            throw new UsernameNotFoundException("User not found");
//        }
//        System.out.println("User found: " + user.getUsername());
//        System.out.println("Database password (hashed): " + user.getPassword());
//        return new UserPrincipal(user);
//
   }
}
