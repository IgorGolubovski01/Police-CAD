package com.example.demo.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Dispatcher extends User{
    private String fName;
    private String lName;

}
