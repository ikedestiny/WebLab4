package com.example.backend.util;

import com.example.backend.model.Request;
import com.example.backend.model.Result;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class Checker {
public static Result check(Request request){
    //TODO checks will happen here
    double x = request.getX();
    double y = request.getY();
    boolean res = false;
    Result result = new Result();
    result.setX(x);
    result.setY(y);
    result.setVersion(1L);
    result.setR(request.getR());
    result.setRecieved(request.getRecieved());

    if (x > 0 && y >0){
        res = false;
    } else if (x > 0 && y < 0) {
        res = isInCurve(request);
    } else if (x < 0 && y >0) {
        res = isInRectangle(request);
    }else {
        res = isInTriangle(request);
    }


    result.setInArea(res);
    Duration execTime =  Duration.between(request.getRecieved().toInstant(), Timestamp.valueOf(LocalDateTime.now()).toInstant());
    result.setExecutionTime(String.valueOf(Math.abs(execTime.getNano())));
    return result;
}


    public static boolean isInCurve(Request result){
        if ((result.getX() < 0 || result.getY() > 0)) {
            return false;
        }
        System.out.println(new Point(result.getX(), result.getY()).radiusToCenter());
        return new Point(result.getX(), result.getY()).radiusToCenter() <= result.getR();
    }

    public static boolean isInTriangle(Request result){
        if ((result.getX() > 0 || result.getY() > 0)) {
            return false;
        }
        Point checkPoint = new Point(result.getX(), result.getY());
        Point center = new Point(0, 0);
        Point Ox = new Point(-result.getR() / 2, 0);
        Point Oy = new Point(0, -result.getR());

        Triangle main = new Triangle(center, Ox, Oy);
        Triangle t1 = new Triangle(center, Ox, checkPoint);
        Triangle t2 = new Triangle(center, Oy, checkPoint);
        Triangle t3 = new Triangle(Oy, Ox, checkPoint);


        return main.area() >= t1.area() + t2.area() + t3.area();
    }

    public static  boolean isInRectangle(Request result){
        if (result.getX()>0 || result.getY() < 0){
            return false;
        }
        return result.getY() <= result.getR() && -result.getX() <= result.getR();
    }
}
