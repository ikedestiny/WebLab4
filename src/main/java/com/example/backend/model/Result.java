package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
public class Result implements Serializable {
    private double x;
    private double y;
    private double r;
    private Timestamp recieved;
    private String executionTime;
    private boolean inArea;
    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
