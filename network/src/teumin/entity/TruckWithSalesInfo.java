package teumin.entity;

import java.time.LocalTime;

public class TruckWithSalesInfo extends Truck {
    private boolean isOpen;
    private LocalTime begin;
    private LocalTime end;
    private Address address;

    public TruckWithSalesInfo(String name, String introduction, String explanation, String category, int proven, Bytes evidence, Bytes icon, boolean isOpen, LocalTime begin, LocalTime end, Address address) {
        super(name, introduction, explanation, category, proven, evidence, icon);
        this.isOpen = isOpen;
        this.begin = begin;
        this.end = end;
        this.address = address;
    }

    public boolean isOpen() {
        return isOpen;
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

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
