package teumin.entity;

import javafx.scene.image.Image;

public class Truck {
    private String name;
    private String introduction;
    private String explanation;
    private String category;
    private Image evidence;
    private Image icon;

    public Truck(String name, String introduction, String explanation, String category, Image evidence, Image icon) {
        this.name = name;
        this.introduction = introduction;
        this.explanation = explanation;
        this.category = category;
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

    public Image getEvidence() {
        return evidence;
    }

    public Image getIcon() {
        return icon;
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

    public void setEvidence(Image evidence) {
        this.evidence = evidence;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
