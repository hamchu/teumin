package teumin.entity;

import java.io.Serializable;

public class Address implements Serializable {
    private String name;
    private double x;
    private double y;

    public Address(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static double getDistance(Address A, Address B) {
        double distance = Math.sin(deg2rad(A.y)) * Math.sin(deg2rad(B.y)) + Math.cos(deg2rad(A.y)) * Math.cos(deg2rad(B.y)) * Math.cos(deg2rad(A.x - B.x));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515 * 1.609344;
        return distance;
    }

    private static double deg2rad(double deg) {
        return deg * Math.PI / 180.0;
    }

    private static double rad2deg(double rad) {
        return rad * 180.0 / Math.PI;
    }
}
