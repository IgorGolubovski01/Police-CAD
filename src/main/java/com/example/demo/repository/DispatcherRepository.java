package com.example.demo.repository;

import com.example.demo.entity.Dispatcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispatcherRepository extends JpaRepository<Dispatcher, Long> {
    Dispatcher findByUsername(String username);
}
