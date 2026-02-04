package com.example.demo.repository;

import com.example.demo.entity.UnitRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRecordRepository extends JpaRepository<UnitRecord, Long> {
    List<UnitRecord> findAllByUnitId(Long uId);
}
