package com.example.backend.util;

import com.example.backend.model.Request;
import com.example.backend.model.Result;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class Checker {
    public static Result check(Request request) {
        // Extract input values
        double x = request.getX();
        double y = request.getY();
        double r = request.getR();

        // Initialize the result
        Result result = new Result();
        result.setX(x);
        result.setY(y);
        result.setVersion(1L);
        result.setR(r);
        result.setRecieved(request.getRecieved());
        result.setClicked(request.isClicked());

        // Check if the point is in the area
        boolean res;
        if (x > 0 && y > 0) {
            res = false; // First quadrant: not in any shape
        } else if (x > 0 && y < 0) {
            res = isInCurve(request); // Fourth quadrant: check the curve
        } else if (x < 0 && y > 0) {
            res = isInRectangle(request); // Second quadrant: check the rectangle
        } else {

            if(request.getR() == 0){res = false;}else{
            res = isInTriangle(request); // Third quadrant: check the triangle
        }}
        result.setInArea(res);

        // Calculate execution time (consistent use of Timestamp)
        Duration execTime = Duration.between(request.getRecieved().toInstant(), Timestamp.valueOf(LocalDateTime.now()).toInstant());
        result.setExecutionTime(String.valueOf(execTime.toMillis()));

        return result;
    }

    public static boolean isInCurve(Request result) {
        double x = result.getX();
        double y = result.getY();
        double r = result.getR();

        // Curve check
        if (x < 0 || y > 0) {
            return false; // Not in the fourth quadrant
        }
        return Math.sqrt(x * x + y * y) <= r; // Circle equation: radius check
    }

    public static boolean isInTriangle(Request result) {
        double x = result.getX();
        double y = result.getY();
        double r = result.getR();

        if (x > 0 || y > 0) {
            return false; // Not in the third quadrant
        }

        // Triangle vertices
        Point center = new Point(0, 0);
        Point Ox = new Point(-r / 2, 0);
        Point Oy = new Point(0, -r);

        // Check if the point lies within the triangle
        Triangle main = new Triangle(center, Ox, Oy);
        Triangle t1 = new Triangle(center, Ox, new Point(x, y));
        Triangle t2 = new Triangle(center, Oy, new Point(x, y));
        Triangle t3 = new Triangle(Oy, Ox, new Point(x, y));

        return main.area() >= t1.area() + t2.area() + t3.area();
    }

    public static boolean isInRectangle(Request result) {
        double x = result.getX();
        double y = result.getY();
        double r = result.getR();

        if (x > 0 || y < 0) {
            return false; // Not in the second quadrant
        }
        return y <= r && -x <= r; // Rectangle bounds
    }
}
