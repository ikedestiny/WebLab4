package com.example.backend.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Triangle {
    private Point p1;
    private Point p2;
    private Point p3;


    public double area(){
        return (float) (0.5 * Math.abs(
                (p1.x() * (p2.y() - p3.y())) +
                        (p2.x() * (p3.y() - p1.y())) +
                        (p3.x() * (p1.y() - p2.y()))
        ));

    }

}
