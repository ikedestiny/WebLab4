package com.example.backend.util;

public class Point {

  private double x;
  private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double  radiusToCenter(){
            return  Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }
}
