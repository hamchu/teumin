package teumin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SalesInfo {
    String truckName;
    LocalDate date;
    LocalTime begin;
    LocalTime end;
    Address address;

    public SalesInfo(String truckName, LocalDate date, LocalTime begin, LocalTime end, Address address) {
        this.truckName = truckName;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.address = address;
    }

    public String getTruckName() {
        return truckName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return address.getName();
    }

    public double getX() {
        return address.getX();
    }

    public double getY() {
        return address.getY();
    }
}
