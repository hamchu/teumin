package teumin.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Recruit implements Serializable {
    private int number;
    private String name;
    private LocalDate recruit_begin;
    private LocalDate recruit_end;
    private LocalDate sales_begin;
    private LocalDate sales_end;
    private String address;
    private String explanation;
    private String reference_url;

    public Recruit(int number, String name, LocalDate recruit_begin, LocalDate recruit_end, LocalDate sales_begin, LocalDate sales_end, String address, String explanation, String reference_url) {
        this.number = number;
        this.name = name;
        this.recruit_begin = recruit_begin;
        this.recruit_end = recruit_end;
        this.sales_begin = sales_begin;
        this.sales_end = sales_end;
        this.address = address;
        this.explanation = explanation;
        this.reference_url = reference_url;
    }

    public int getNumber() { return number; }

    public String getName() {
        return name;
    }

    public LocalDate getRecruit_begin() {
        return recruit_begin;
    }

    public LocalDate getRecruit_end() {
        return recruit_end;
    }

    public LocalDate getSales_begin() {
        return sales_begin;
    }

    public LocalDate getSales_end() {
        return sales_end;
    }

    public String getAddress() {
        return address;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getReference_url() {
        return reference_url;
    }
}
