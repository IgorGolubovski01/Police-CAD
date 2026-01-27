package com.example.demo.repository;

import com.example.demo.entity.CriminalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriminalRecordRepository extends JpaRepository<CriminalRecord, Long> {
}
