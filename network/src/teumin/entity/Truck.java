package teumin.entity;

import java.io.Serializable;


public class Truck implements Serializable {
    private String name;
    private String introduction;
    private String explanation;
    private String category;
    private int proven;
    private Bytes evidence;
    private Bytes icon;

    public Truck(String name, String introduction, String explanation, String category, int proven, Bytes evidence, Bytes icon) {
        this.name = name;
        this.introduction = introduction;
        this.explanation = explanation;
        this.category = category;
        this.proven = proven;
        this.evidence = evidence;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getCategory() {
        return category;
    }

    public Bytes getEvidence() {
        return evidence;
    }

    public Bytes getIcon() {
        return icon;
    }

    public int getProven() {
        return proven;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEvidence(Bytes evidence) {
        this.evidence = evidence;
    }

    public void setIcon(Bytes icon) {
        this.icon = icon;
    }

    public void setProven(int proven) {
        this.proven = proven;
    }
}
