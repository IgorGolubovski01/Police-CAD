package com.example.demo.repository;


import com.example.demo.entity.IncidentUnitRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentUnitRelRepository extends JpaRepository<IncidentUnitRel, Long> {
    List<IncidentUnitRel> findByIncidentId(Long iId);
}
