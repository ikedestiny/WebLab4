package com.example.backend.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Request implements Serializable {
    private double x;
    private double y;
    private double r;
    private Timestamp recieved;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean clicked;
}
